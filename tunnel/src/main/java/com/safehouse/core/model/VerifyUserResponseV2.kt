package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VerifyUserResponseV2(
    @SerializedName ("statusCode")
    @Expose
    var statusCode: Int?,

    @SerializedName ("message")
    @Expose
    var message: String?,

    @SerializedName ("error")
    @Expose
    var error: String?
)