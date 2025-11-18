package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetMyIpResponse {
    @SerializedName("ip")
    @Expose
    val userIp: String? = null

    override fun toString(): String {
        return "GetMyIpResponse(userIp=$userIp)"
    }
}