package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetExcludeAppResponse {
    @Expose
    @SerializedName("title")
    val title: String? = null

    @Expose
    @SerializedName("packageName")
    val packageName: String? = null

    @Expose
    @SerializedName("active")
    val boolean = false
}