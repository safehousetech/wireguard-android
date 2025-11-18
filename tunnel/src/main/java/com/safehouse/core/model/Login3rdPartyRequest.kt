package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Login3rdPartyRequest (
    @Expose
    @SerializedName("phone")
    var phone : String,
    @Expose
    @SerializedName("deviceId")
    var deviceId : String,
    @Expose
    @SerializedName("deviceName")
    var deviceName : String,
    @Expose
    @SerializedName("deviceType")
    var deviceType : String
)