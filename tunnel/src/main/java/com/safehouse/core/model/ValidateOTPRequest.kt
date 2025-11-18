package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ValidateOTPRequest(
    @Expose @SerializedName("otp") val otp: String,
    @Expose @SerializedName("phone") val phone: String? = null,
    @Expose @SerializedName("email") val email: String? = null,
)

