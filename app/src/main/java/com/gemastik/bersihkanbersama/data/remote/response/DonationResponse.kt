package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class DonationResponse(
    @field:SerializedName("DonationId")
    val donationId: String?,

    @field:SerializedName("UserName")
    val userName: String?,

    @field:SerializedName("TotalDonation")
    val totalDonation: Int?
)