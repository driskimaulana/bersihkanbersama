package com.gemastik.bersihkanbersama.data.models

data class DonationActivityModel(
    val totalDonation: Int,
    val donationHistory: List<DonationModel>
)