package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UploadIpRequest (
    @Expose
    @SerializedName("beforeVpnIp")
    var beforeVpnIp : String,
    @Expose
    @SerializedName("afterVpnIp")
    var afterVpnIp : String,
)