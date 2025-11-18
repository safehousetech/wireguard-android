package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PolicyDetailsResponse {
    @Expose @SerializedName("url") val policyUrl: String = ""

    @Expose @SerializedName("licenseType") val licenseType: String = ""

    @Expose @SerializedName("proposal") val proposal: String = ""

    @Expose @SerializedName("status") val policyStatus: String = ""

    @Expose @SerializedName("expirationDate") val policyExpirationDate: String = ""

    @Expose @SerializedName("startDate") val policyStartDate: String = ""

    @Expose @SerializedName("sum") val sumInsured: String = ""
}
