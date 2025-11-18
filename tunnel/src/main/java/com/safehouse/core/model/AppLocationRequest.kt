package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AppLocationRequest(
    @Expose
    @SerializedName("os")
    private val osType: String,

    @Expose
    @SerializedName("apps")
    private val appList: List<String>
)