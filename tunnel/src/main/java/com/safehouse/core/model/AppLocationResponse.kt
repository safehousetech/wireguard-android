package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AppLocationResponse {
    @Expose
    @SerializedName("appOrigins")
    val appOrigins: List<appDataLocationData> = listOf()

    class appDataLocationData {
        @Expose
        @SerializedName("country")
        val country: String? = null

        @Expose
        @SerializedName("packageName")
        val packageName: String? = null

        @Expose
        @SerializedName("name")
        val name: String? = null
    }
}