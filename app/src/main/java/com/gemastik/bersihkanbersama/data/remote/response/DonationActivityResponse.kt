package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class DonationActivityResponse(
    @field:SerializedName("TotalDonation")
    val totalDonation: Int,

    @field:SerializedName("DonationHistory")
    val donationHistory: List<DonationResponse>,
)