package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RefreshTokenResponse {
    @Expose
    @SerializedName("token")
    val token: String? = null

    @Expose
    @SerializedName("refreshToken")
    val refreshToken: String? = null

    @Expose
    @SerializedName("freeForever")
    val freeForever: Boolean? = null

    @Expose
    @SerializedName("vpn")
    val vpn: Boolean? = null

    @Expose
    @SerializedName("tracker")
    val tracker: Boolean? = null
}