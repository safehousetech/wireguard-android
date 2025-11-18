package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginExternalResponse {
    @Expose
    @SerializedName("token")
    val token: String? = null
    @Expose
    @SerializedName("refreshToken")
    val refreshToken: String? = null
    @Expose
    @SerializedName("activationDate")
    val activationDate: String? = null
    @Expose
    @SerializedName("expiryDate")
    val expiryDate: String? = null
    @Expose
    @SerializedName("licenseType")
    val licenseType: String? = null
}