/*
 * Copyright © 2017-2019 WireGuard LLC. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.wireguard.android.backend;

import androidx.annotation.Nullable;

import android.content.Context;
import android.util.Log;

import com.wireguard.android.backend.BackendException.Reason;
import com.wireguard.android.backend.Tunnel.State;
import com.wireguard.android.util.RootShell;
import com.wireguard.android.util.ToolsInstaller;
import com.wireguard.config.Config;
import com.wireguard.crypto.Key;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.HashMap;

import java9.util.stream.Collectors;
import java9.util.stream.Stream;

/**
 * WireGuard backend that uses {@code wg-quick} to implement tunnel configuration.
 */

public final class WgQuickBackend implements Backend {
    private static final String TAG = "WireGuard/" + WgQuickBackend.class.getSimpleName();

    private final RootShell rootShell;
    private final ToolsInstaller toolsInstaller;
    private final File localTemporaryDir;
    private final Map<Tunnel, Config> runningConfigs = new HashMap<>();
    private final Set<TunnelStateChangeNotificationReceiver> notifiers = new HashSet<>();

    public WgQuickBackend(final Context context, final RootShell rootShell, final ToolsInstaller toolsInstaller) {
        localTemporaryDir = new File(context.getCacheDir(), "tmp");
        this.rootShell = rootShell;
        this.toolsInstaller = toolsInstaller;
    }

    @Override
    public Set<String> getRunningTunnelNames() {
        final List<String> output = new ArrayList<>();
        // Don't throw an exception here or nothing will show up in the UI.
        try {
            toolsInstaller.ensureToolsAvailable();
            if (rootShell.run(output, "wg show interfaces") != 0 || output.isEmpty())
                return Collections.emptySet();
        } catch (final Exception e) {
            Log.w(TAG, "Unable to enumerate running tunnels", e);
            return Collections.emptySet();
        }
        // wg puts all interface names on the same line. Split them into separate elements.
        return Stream.of(output.get(0).split(" ")).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public State getState(final Tunnel tunnel) {
        return getRunningTunnelNames().contains(tunnel.getName()) ? State.UP : State.DOWN;
    }

    @Override
    public Statistics getStatistics(final Tunnel tunnel) {
        final Statistics stats = new Statistics();
        final Collection<String> output = new ArrayList<>();
        try {
            if (rootShell.run(output, String.format("wg show '%s' transfer", tunnel.getName())) != 0)
                return stats;
        } catch (final Exception ignored) {
            return stats;
        }
        for (final String line : output) {
            final String[] parts = line.split("\\t");
            if (parts.length != 3)
                continue;
            try {
                stats.add(Key.fromBase64(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2]));
            } catch (final Exception ignored) {
            }
        }
        return stats;
    }

    @Override
    public String getVersion() throws Exception {
        final List<String> output = new ArrayList<>();
        if (rootShell.run(output, "cat /sys/module/wireguard/version") != 0 || output.isEmpty())
            throw new BackendException(Reason.UNKNOWN_KERNEL_MODULE_NAME);
        return output.get(0);
    }

    @Override
    public State setState(final Tunnel tunnel, State state, @Nullable final Config config) throws Exception {
        final State originalState = getState(tunnel);
        final Config originalConfig = runningConfigs.get(tunnel);

        if (state == State.TOGGLE)
            state = originalState == State.UP ? State.DOWN : State.UP;
        if ((state == State.UP && originalState == State.UP && originalConfig != null && originalConfig == config) ||
                (state == State.DOWN && originalState == State.DOWN))
            return originalState;
        if (state == State.UP) {
            toolsInstaller.ensureToolsAvailable();
            if (originalState == State.UP)
                setStateInternal(tunnel, originalConfig == null ? config : originalConfig, State.DOWN);
            try {
                setStateInternal(tunnel, config, State.UP);
            } catch(final Exception e) {
                if (originalState == State.UP && originalConfig != null)
                    setStateInternal(tunnel, originalConfig, State.UP);
                throw e;
            }
        } else if (state == State.DOWN) {
            setStateInternal(tunnel, originalConfig == null ? config : originalConfig, State.DOWN);
        }
        return state;
    }

    private void setStateInternal(final Tunnel tunnel, @Nullable final Config config, final State state) throws Exception {
        Log.i(TAG, "Bringing tunnel " + tunnel.getName() + " " + state);

        Objects.requireNonNull(config, "Trying to set state up with a null config");

        final File tempFile = new File(localTemporaryDir, tunnel.getName() + ".conf");
        try (final FileOutputStream stream = new FileOutputStream(tempFile, false)) {
            stream.write(config.toWgQuickString().getBytes(StandardCharsets.UTF_8));
        }
        String command = String.format("wg-quick %s '%s'",
                state.toString().toLowerCase(Locale.ENGLISH), tempFile.getAbsolutePath());
        if (state == State.UP)
            command = "cat /sys/module/wireguard/version && " + command;
        final int result = rootShell.run(null, command);
        // noinspection ResultOfMethodCallIgnored
        tempFile.delete();
        if (result != 0)
            throw new BackendException(Reason.WG_QUICK_CONFIG_ERROR_CODE, result);

        if (state == State.UP)
            runningConfigs.put(tunnel, config);
        else
            runningConfigs.remove(tunnel);

        for (final TunnelStateChangeNotificationReceiver notifier : notifiers)
            notifier.tunnelStateChange(tunnel, state);
    }

    @Override
    public void registerStateChangeNotification(final TunnelStateChangeNotificationReceiver receiver) {
        notifiers.add(receiver);
    }

    @Override
    public void unregisterStateChangeNotification(final TunnelStateChangeNotificationReceiver receiver) {
        notifiers.remove(receiver);
    }
}
