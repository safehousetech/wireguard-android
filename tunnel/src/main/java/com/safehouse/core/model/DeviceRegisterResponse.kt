package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DeviceRegisterResponse {
    @Expose
    @SerializedName("deviceId")
    val deviceId: String = ""
}