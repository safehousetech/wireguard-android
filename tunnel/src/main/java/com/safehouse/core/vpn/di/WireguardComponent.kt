package com.safehouse.core.vpn.di


import com.safehouse.core.vpn.TunnelManager
import com.safehouse.android.backend.GoBackend
import dagger.Subcomponent

@Subcomponent
interface WireguardComponent {
  fun inject(receiver: TunnelManager.IntentReceiver)
  fun inject(service: GoBackend.VpnService)

  @Subcomponent.Builder
  interface Builder {
    fun build(): WireguardComponent
  }
}
