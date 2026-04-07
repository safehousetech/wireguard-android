package com.safehouse.core.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaymentData(
    @SerializedName("email")
    @Expose
    private val email: String?,
    @SerializedName("phone")
    @Expose
    private val phone: String?,
    @SerializedName("productId")
    @Expose
    private val productId: String?,
    @SerializedName("price")
    @Expose
    private val price: Int?= null
)
