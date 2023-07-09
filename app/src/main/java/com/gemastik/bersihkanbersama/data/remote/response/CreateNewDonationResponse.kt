package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class CreateNewDonationResponse(
    @field:SerializedName("donationId")
    val donationId: String
)
