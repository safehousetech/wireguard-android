package com.safehouse.core.model

import com.google.gson.annotations.SerializedName

class UpdateTransactionResponse {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("license_type")
    var licenseType: String? = null
    @SerializedName("license_length")
    var licenseLength: String? = null
    @SerializedName("expiry_date")
    var expiryDate: String? = null
    @SerializedName("is_claimed")
    var isClaimed: Boolean? = null
    @SerializedName("license_hash")
    var licenseHash: String? = null
    @SerializedName("created")
    var created: String? = null
    @SerializedName("updated")
    var updated: String? = null
    @SerializedName("claimed_user_id")
    var claimedUserId: Int? = null
    @SerializedName("customer_id")
    var customerId: Int? = null
    @SerializedName("sub_customer_id")
    var subCustomerId: Int? = null
    @SerializedName("is_active")
    var isActive: Boolean? = null
    @SerializedName("tracking")
    var tracking: String? = null
    @SerializedName("product_type")
    var productType: String? = null
    @SerializedName("price")
    var price: Int? = null
    @SerializedName("tui")
    var tui: String? = null
    @SerializedName("actions")
    var actions: String? = null
    @SerializedName("vendor")
    var vendor: String? = null
    @SerializedName("trail")
    var trail: Boolean? = null
    @SerializedName("receipt")
    var receipt: String? = null
    @SerializedName("platform")
    var platform: String? = null
    @SerializedName("activation_date")
    var activationDate: String? = null
    @SerializedName("purchase_receipt")
    var purchaseReceipt: String? = null
    @SerializedName("country_code")
    var countryCode: String? = null
    @SerializedName("pack_id")
    var packId: String? = null
    @SerializedName("coupon_id")
    var couponId: String? = null
}