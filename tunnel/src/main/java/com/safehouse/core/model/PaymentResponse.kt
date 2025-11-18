package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("paymentLink")
    @Expose
    val paymentLink: String,
    @SerializedName("orderId")
    @Expose
    val orderId: String
)
