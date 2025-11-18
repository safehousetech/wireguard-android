package com.safehouse.core.vpn

import android.content.Context
import kotlinx.coroutines.launch

class ApplicationPreferencesChangeCallback(val context: Context, val tunnelManager: TunnelManager) {
  fun restart() {
    context.restartApplication()
  }

  fun restartActiveTunnels() {
    applicationScope.launch{
      tunnelManager.restoreState(true)
    }

  }
}
