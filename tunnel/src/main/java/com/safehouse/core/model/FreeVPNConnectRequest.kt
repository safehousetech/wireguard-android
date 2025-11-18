package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FreeVPNConnectRequest(
    @SerializedName("publicKey")
    @Expose
    private val publicKey: String?,
    @SerializedName("licenseType")
    @Expose
    private val licenseType: String?,
    @SerializedName("phone")
    @Expose
    private val phone: String?,
    @SerializedName("email")
    @Expose
    private val email: String?,
    @SerializedName("mode")
    @Expose
    private val mode: String,
    @SerializedName("beforeVpnIp")
    @Expose
    private val beforeVpnIp: String,
)

