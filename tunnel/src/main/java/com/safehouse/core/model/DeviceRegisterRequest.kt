package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeviceRegisterRequest (
    @Expose @SerializedName("deviceName")
    val deviceName: String,

    @Expose @SerializedName("model")
    val model: String,

    @Expose @SerializedName("brand")
    val brand: String,

    @Expose @SerializedName("serialNumber")
    val serialNumber: String,

    @Expose @SerializedName("osType")
    val osType: String = "android",

    @Expose @SerializedName("trackerPin")
    val trackerPin: String? = null,

    @Expose @SerializedName("fcmToken")
    val fcmToken: String,

    @Expose @SerializedName("isParentalLockEnabled")
    val isParentalLockEnabled: Boolean,

    @Expose @SerializedName("isLockEnabled")
    val isLockEnabled: Boolean,

    @Expose @SerializedName("isSirenEnabled")
    val isSirenEnabled: Boolean,

    @Expose @SerializedName("isLocationEnabled")
    val isLocationEnabled: Boolean,

    @Expose @SerializedName("isSnapshotEnabled")
    val isSnapshotEnabled: Boolean,
)