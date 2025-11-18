/*
 * Copyright © 2017-2023 WireGuard LLC. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */
package com.safehouse.core.vpn

import android.content.res.Resources
import android.os.RemoteException
import com.safehouse.android.backend.BackendException
import com.safehouse.android.util.RootShell.RootShellException
import com.safehouse.config.BadConfigException
import com.safehouse.config.InetEndpoint
import com.safehouse.config.InetNetwork
import com.safehouse.config.ParseException
import com.safehouse.crypto.Key
import com.safehouse.crypto.KeyFormatException
import java.net.InetAddress

object ErrorMessages {

    private val BCE_REASON_MAP = mapOf(
        BadConfigException.Reason.INVALID_KEY to "Invalid key",
        BadConfigException.Reason.INVALID_NUMBER to "Invalid number",
        BadConfigException.Reason.INVALID_VALUE to "Invalid value",
        BadConfigException.Reason.MISSING_ATTRIBUTE to "Missing attribute",
        BadConfigException.Reason.MISSING_SECTION to "Missing section",
        BadConfigException.Reason.SYNTAX_ERROR to "Syntax error",
        BadConfigException.Reason.UNKNOWN_ATTRIBUTE to "Unknown attribute",
        BadConfigException.Reason.UNKNOWN_SECTION to "Unknown section"
    )

    private val BE_REASON_MAP = mapOf(
        BackendException.Reason.UNKNOWN_KERNEL_MODULE_NAME to "Unable to determine kernel module version",
        BackendException.Reason.WG_QUICK_CONFIG_ERROR_CODE to "Unable to configure tunnel",
        BackendException.Reason.TUNNEL_MISSING_CONFIG to "Trying to bring up a tunnel with no config",
        BackendException.Reason.VPN_NOT_AUTHORIZED to "VPN service not authorized by user",
        BackendException.Reason.UNABLE_TO_START_VPN to "Unable to start Android VPN service",
        BackendException.Reason.TUN_CREATION_ERROR to "Unable to create tun device",
        BackendException.Reason.GO_ACTIVATION_ERROR_CODE to "Unable to turn tunnel on",
        BackendException.Reason.DNS_RESOLUTION_FAILURE to "DNS resolution failure"
    )

    private val KFE_FORMAT_MAP = mapOf(
        Key.Format.BASE64 to ": WireGuard base64 keys must be 44 characters (32 bytes)",
        Key.Format.BINARY to ": WireGuard keys must be 32 bytes",
        Key.Format.HEX to ": WireGuard hex keys must be 64 characters (32 bytes)"
    )

    private val KFE_TYPE_MAP = mapOf(
        KeyFormatException.Type.CONTENTS to "Bad characters in key",
        KeyFormatException.Type.LENGTH to "Incorrect key length"
    )

    private val PE_CLASS_MAP = mapOf(
        InetAddress::class.java to "IP address",
        InetEndpoint::class.java to "endpoint",
        InetNetwork::class.java to "IP network",
        Int::class.java to "number"
    )

    private val RSE_REASON_MAP = mapOf(
        RootShellException.Reason.NO_ROOT_ACCESS to "Root access denied",
        RootShellException.Reason.SHELL_MARKER_COUNT_ERROR to "Shell marker count error",
        RootShellException.Reason.SHELL_EXIT_STATUS_READ_ERROR to "Unable to read shell exit status",
        RootShellException.Reason.SHELL_START_ERROR to "Unable to start shell",
        RootShellException.Reason.CREATE_BIN_DIR_ERROR to "Unable to create bin directory",
        RootShellException.Reason.CREATE_TEMP_DIR_ERROR to "Unable to create temp directory"
    )

    operator fun get(throwable: Throwable?): String {
        if (throwable == null) return "Unknown error"

        val rootCause = rootCause(throwable)

        return when {
            rootCause is BadConfigException -> {
                val reason = getBadConfigExceptionReason(rootCause)
                val context = if (rootCause.location == BadConfigException.Location.TOP_LEVEL) {
                    "${rootCause.section.name}"
                } else {
                    "${rootCause.section.name}'s ${rootCause.location.name}"
                }
                val explanation = getBadConfigExceptionExplanation(rootCause)
                "$reason in $context$explanation"
            }

            rootCause is BackendException ->
                BE_REASON_MAP[rootCause.reason] ?: "Backend error"

            rootCause is RootShellException ->
                RSE_REASON_MAP[rootCause.reason] ?: "Root shell error"

            rootCause.localizedMessage != null ->
                rootCause.localizedMessage!!

            else -> {
                val errorType = rootCause.javaClass.simpleName
                "Unknown error: $errorType"
            }
        }
    }

    private fun getBadConfigExceptionExplanation(bce: BadConfigException): String {
        return when {
            bce.cause is KeyFormatException -> {
                val kfe = bce.cause as KeyFormatException
                if (kfe.type == KeyFormatException.Type.LENGTH)
                    KFE_FORMAT_MAP[kfe.format] ?: ""
                else ""
            }

            bce.cause is ParseException -> {
                val pe = bce.cause as ParseException
                if (pe.localizedMessage != null) ": ${pe.localizedMessage}" else ""
            }

            bce.location == BadConfigException.Location.LISTEN_PORT ->
                ": Must be a valid UDP port number"

            bce.location == BadConfigException.Location.MTU ->
                ": Must be positive"

            bce.location == BadConfigException.Location.PERSISTENT_KEEPALIVE ->
                ": Must be positive and no more than 65535"

            else -> ""
        }
    }

    private fun getBadConfigExceptionReason(bce: BadConfigException): String {
        return when {
            bce.cause is KeyFormatException -> {
                val kfe = bce.cause as KeyFormatException
                KFE_TYPE_MAP[kfe.type] ?: "Key error"
            }

            bce.cause is ParseException -> {
                val pe = bce.cause as ParseException
                val type = PE_CLASS_MAP[pe.parsingClass] ?: "number"
                "Cannot parse $type “${pe.text}”"
            }

            else -> BCE_REASON_MAP[bce.reason] ?: "Bad configuration"
        }
    }

    private fun rootCause(throwable: Throwable): Throwable {
        var cause = throwable
        while (cause.cause != null) {
            if (cause is BadConfigException ||
                cause is BackendException ||
                cause is RootShellException
            ) break

            val next = cause.cause!!
            if (next is RemoteException) break
            cause = next
        }
        return cause
    }
}

