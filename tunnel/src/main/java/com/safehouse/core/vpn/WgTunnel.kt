package com.safehouse.core.vpn

import com.safehouse.android.backend.Tunnel

class WgTunnel() : Tunnel {
    override fun getName(): String {
        return "SH_SDK_Demo"
    }

    override fun onStateChange(newState: Tunnel.State) {

    }
}