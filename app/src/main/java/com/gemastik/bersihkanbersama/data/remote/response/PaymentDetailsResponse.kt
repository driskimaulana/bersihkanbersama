package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class PaymentDetailsResponse(
    @SerializedName("paymentDetails")
    val paymentDetails: PaymentDetails
)

data class PaymentDetails(
    @SerializedName("Amount")
    val amount: Double,

    @SerializedName("PaymentUrl")
    val paymentUrl: String,

    @SerializedName("Status")
    val status: String
)
