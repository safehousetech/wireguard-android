package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MyPlanResponse(
    @SerializedName("activeLicenses")
    @Expose
    val activeLicenses: List<PlanLicense>,

    @SerializedName("upcomingLicenses")
    @Expose
    val upcomingLicenses: List<PlanLicense>
)

data class PlanLicense(
    @SerializedName("expiryDate")
    @Expose
    val expiryDate: String,

    @SerializedName("activationDate")
    @Expose
    val activationDate: String,

    @SerializedName("productId")
    @Expose
    val productId: String
)
