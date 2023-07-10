package com.gemastik.bersihkanbersama.data.models

data class CreateNewDonation (
    val items: List<DonationItem>,
    val isAnonim: Boolean,
)