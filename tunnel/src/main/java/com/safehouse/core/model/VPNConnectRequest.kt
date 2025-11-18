package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VPNConnectRequest(
    @SerializedName("publicKey")
    @Expose
    private val publicKey: String?,
    @SerializedName("region")
    @Expose
    private val region: String?,
    @SerializedName("mode")
    @Expose
    private val mode: String
)