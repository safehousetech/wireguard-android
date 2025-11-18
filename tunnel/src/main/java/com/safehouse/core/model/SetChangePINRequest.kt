package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SetChangePINRequest (
    @Expose
    @SerializedName("pin")
    var pin : String
)