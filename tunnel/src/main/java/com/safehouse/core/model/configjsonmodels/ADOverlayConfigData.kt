package com.safehouse.core.model.configjsonmodels

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ADOverlayRemoteConfigData(
  @SerializedName("show_screen") var showOverlay: Boolean = false
)
