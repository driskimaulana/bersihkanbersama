package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class PaymentDetailsResponse(
    @field:SerializedName("Amount")
    val amount: Int,

    @field:SerializedName("PaymentUrl")
    val paymentUrl: String,

    @field:SerializedName("Status")
    val status: String
)
