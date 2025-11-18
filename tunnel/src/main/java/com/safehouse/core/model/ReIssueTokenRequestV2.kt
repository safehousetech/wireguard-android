package com.safehouse.core.model

import javax.inject.Inject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class ReIssueTokenRequestV2(
    @SerializedName("deviceId") @Expose private val deviceId: String,
    @SerializedName("deviceName") @Expose private val deviceName: String,
    @SerializedName("deviceType") @Expose private val deviceType: String,
    @SerializedName("otp") @Expose private val otp: String?,
    @SerializedName("email") @Expose private val email: String?,
    @SerializedName("phone") @Expose private val phone: String?,
    @SerializedName("alternateEmail") @Expose private val alternateEmail: String?,
    @SerializedName("alternatePhone") @Expose private val alternatePhone: String?,
    @SerializedName("deepLinkSource") @Expose private val deepLinkSource: String?,
    @SerializedName("appName") @Expose private val appName: String?,
    @SerializedName("customerId") @Expose private val customerId: String?
)