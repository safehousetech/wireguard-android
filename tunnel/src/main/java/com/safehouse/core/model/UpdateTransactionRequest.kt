package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdateTransactionRequest(
    @Expose
    @SerializedName("isUpgrade")
    val isUpgrade: Boolean = false,
    
    @Expose
    @SerializedName("deviceId")
    val deviceId: String?,

    @Expose
    @SerializedName("license")
    val license: String?,

    @Expose
    @SerializedName("licenseLength")
    var licenseLength: String,

    @Expose
    @SerializedName("licenseType")
    var licenseType: String,

    @Expose
    @SerializedName("receipt")
    var receipt: Receipt
) {
    class Receipt(
        @Expose
        @SerializedName("packageName")
        var packageName: String,

        @Expose
        @SerializedName("productId")
        var productId: String,

        @Expose
        @SerializedName("purchaseToken")
        var purchaseToken: String
    )
}