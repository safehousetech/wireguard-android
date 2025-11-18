package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RemovePeerRequest (
    @Expose
    @SerializedName("publicKey")
    var publicKey : String,
    @Expose
    @SerializedName("region")
    var region : String,
)