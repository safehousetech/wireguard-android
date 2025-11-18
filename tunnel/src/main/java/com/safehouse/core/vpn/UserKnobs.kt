package com.safehouse.core.vpn

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object UserKnobs {
    private val ENABLE_KERNEL_MODULE = booleanPreferencesKey("enable_kernel_module")
    val enableKernelModule: Flow<Boolean>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[ENABLE_KERNEL_MODULE] ?: false
        }

    suspend fun setEnableKernelModule(enable: Boolean?) {
        TunnelManager.preferencesDataStore.edit {
            if (enable == null)
                it.remove(ENABLE_KERNEL_MODULE)
            else
                it[ENABLE_KERNEL_MODULE] = enable
        }
    }

    private val MULTIPLE_TUNNELS = booleanPreferencesKey("multiple_tunnels")
    val multipleTunnels: Flow<Boolean>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[MULTIPLE_TUNNELS] ?: false
        }

    private val DARK_THEME = booleanPreferencesKey("dark_theme")
    val darkTheme: Flow<Boolean>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[DARK_THEME] ?: false
        }

    suspend fun setDarkTheme(on: Boolean) {
        TunnelManager.preferencesDataStore.edit {
            it[DARK_THEME] = on
        }
    }

    private val ALLOW_REMOTE_CONTROL_INTENTS = booleanPreferencesKey("allow_remote_control_intents")
    val allowRemoteControlIntents: Flow<Boolean>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[ALLOW_REMOTE_CONTROL_INTENTS] ?: false
        }

    private val RESTORE_ON_BOOT = booleanPreferencesKey("restore_on_boot")
    val restoreOnBoot: Flow<Boolean>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[RESTORE_ON_BOOT] ?: false
        }

    private val LAST_USED_TUNNEL = stringPreferencesKey("last_used_tunnel")
    val lastUsedTunnel: Flow<String?>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[LAST_USED_TUNNEL]
        }

    suspend fun setLastUsedTunnel(lastUsedTunnel: String?) {
        TunnelManager.preferencesDataStore.edit {
            if (lastUsedTunnel == null)
                it.remove(LAST_USED_TUNNEL)
            else
                it[LAST_USED_TUNNEL] = lastUsedTunnel
        }
    }

    private val RUNNING_TUNNELS = stringSetPreferencesKey("enabled_configs")
    val runningTunnels: Flow<Set<String>>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[RUNNING_TUNNELS] ?: emptySet()
        }

    suspend fun setRunningTunnels(runningTunnels: Set<String>) {
        TunnelManager.preferencesDataStore.edit {
            if (runningTunnels.isEmpty())
                it.remove(RUNNING_TUNNELS)
            else
                it[RUNNING_TUNNELS] = runningTunnels
        }
    }

    private val IS_ACTIVE_DEFENSE_ENABLED = booleanPreferencesKey("is_ad_on")

    suspend fun setIsActiveDefenseOn(isActiveDefenseOn: Boolean){
        TunnelManager.preferencesDataStore.edit {
            it[IS_ACTIVE_DEFENSE_ENABLED] = isActiveDefenseOn
        }
    }
    val isActiveDefenseOn: Flow<Boolean>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[IS_ACTIVE_DEFENSE_ENABLED]?: false
        }

    private val TOTAL_BLOCKED_COUNT = longPreferencesKey("total_blocked")
    suspend fun setTotalBlocked(totalBlocked: Long){
        TunnelManager.preferencesDataStore.edit {
            it[TOTAL_BLOCKED_COUNT] = totalBlocked
        }
    }
    val totalBlocked: Flow<Long>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[TOTAL_BLOCKED_COUNT]?: 0
        }

    private val BLOCKED_IN_24 = longPreferencesKey("blocked_24")
    suspend fun setBlockedIn24(blockedIn24: Long){
        TunnelManager.preferencesDataStore.edit {
            it[BLOCKED_IN_24] = blockedIn24
        }
    }
    val blockedIn24: Flow<Long>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[BLOCKED_IN_24]?: 0
        }

    private val LAST_UPDATED_STATS = longPreferencesKey("last_updated")
    suspend fun setLastUpdatedStats(lastUpdated: Long){
        TunnelManager.preferencesDataStore.edit {
            it[LAST_UPDATED_STATS] = lastUpdated
        }
    }
    val lastUpdatedStats: Flow<Long>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[LAST_UPDATED_STATS]?: 0
        }

    private val IS_STAT_ADJUST_REQUIRED = booleanPreferencesKey("is_stat_adjust_req")

    suspend fun setIsStatDiffRequired(isRequired: Boolean){
        TunnelManager.preferencesDataStore.edit {
            it[IS_STAT_ADJUST_REQUIRED] = isRequired
        }
    }
    val isStatAdjustRequired: Flow<Boolean>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[IS_STAT_ADJUST_REQUIRED]?: false
        }


    private val TUNNEL_BLOCKED_DIFFERENCE = longPreferencesKey("blocked_count_diff")
    suspend fun setTunnelBlockedDifference(difference: Long){
        TunnelManager.preferencesDataStore.edit {
            it[TUNNEL_BLOCKED_DIFFERENCE] = difference
        }
    }
    val tunnelBlockCountDiff: Flow<Long>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[TUNNEL_BLOCKED_DIFFERENCE]?: 0
        }



    private val CURRENT_TUNNEL_HASH = intPreferencesKey("tunnel_hash")
    suspend fun setCurrentTunnelHash(tunnelHash: Int){
        TunnelManager.preferencesDataStore.edit {
            it[CURRENT_TUNNEL_HASH] = tunnelHash
        }
    }
    val currentTunnelHash: Flow<Int>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[CURRENT_TUNNEL_HASH]?: 0
        }
    private val IS_VPN_ENABLED = booleanPreferencesKey("is_vpn_on")
    suspend fun setIsVpnOn(isVpnOn: Boolean){
        TunnelManager.preferencesDataStore.edit {
            it[IS_VPN_ENABLED] = isVpnOn
        }
    }
    val isVpnEnabled: Flow<Boolean>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[IS_VPN_ENABLED]?: false
        }

    private val UPDATER_NEWER_VERSION_SEEN = stringPreferencesKey("updater_newer_version_seen")
    val updaterNewerVersionSeen: Flow<String?>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[UPDATER_NEWER_VERSION_SEEN]
        }

    suspend fun setUpdaterNewerVersionSeen(newerVersionSeen: String?) {
        TunnelManager.preferencesDataStore.edit {
            if (newerVersionSeen == null)
                it.remove(UPDATER_NEWER_VERSION_SEEN)
            else
                it[UPDATER_NEWER_VERSION_SEEN] = newerVersionSeen
        }
    }

    private val UPDATER_NEWER_VERSION_CONSENTED = stringPreferencesKey("updater_newer_version_consented")
    val updaterNewerVersionConsented: Flow<String?>
        get() = TunnelManager.preferencesDataStore.data.map {
            it[UPDATER_NEWER_VERSION_CONSENTED]
        }

    suspend fun setUpdaterNewerVersionConsented(newerVersionConsented: String?) {
        TunnelManager.preferencesDataStore.edit {
            if (newerVersionConsented == null)
                it.remove(UPDATER_NEWER_VERSION_CONSENTED)
            else
                it[UPDATER_NEWER_VERSION_CONSENTED] = newerVersionConsented
        }
    }
}
