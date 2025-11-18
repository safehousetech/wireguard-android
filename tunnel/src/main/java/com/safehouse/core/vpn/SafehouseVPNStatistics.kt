package com.safehouse.core.vpn

data class SafehouseVPNStatistics (
    val rxBytes : Long,
    val txBytes : Long,
    val lastHandShakeEpoch : Long
)