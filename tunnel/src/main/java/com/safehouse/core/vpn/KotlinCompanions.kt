package com.safehouse.core.vpn

object KotlinCompanions {

    suspend fun streamForDeletion(tunnels: ArrayList<ObservableTunnel>?): List<Unit> {
        return tunnels?.map { tunnel ->
            tunnel.deleteAsync()
        } ?: emptyList()
    }
}