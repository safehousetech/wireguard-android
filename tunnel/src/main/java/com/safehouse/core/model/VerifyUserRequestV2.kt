package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VerifyUserRequestV2(
    @SerializedName("type")
    @Expose
    private val type: String
) {
    enum class CredToVerify(val ref: String) {
        EMAIL("email"),
        PHONE("phone"),
        ALTERNATEMAIL("alternateEmail"),
        ALTERNATEPHONE("alternatePhone")
    }
}