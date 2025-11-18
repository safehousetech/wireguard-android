package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SendDeviceLocationRequest (
    @Expose
    @SerializedName("latitude")
    var latitude : String,

    @Expose
    @SerializedName("longitude")
    var longitude : String,

    @Expose
    @SerializedName("accuracy")
    var accuracy : Float,
    @Expose

    @SerializedName("takenAt")
    var takenAt : String,
)