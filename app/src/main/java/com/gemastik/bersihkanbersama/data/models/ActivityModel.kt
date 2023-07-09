package com.gemastik.bersihkanbersama.data.models

data class ActivityModel(
    val id: String,
    val organizationId: String,
    val title: String,
    val description: String,
    val eventDate: String,
    val coverImage: String,
    val volunteer: VolunteerModel,
    val status: String,
    val rewards: RewardsModel,
    val donationActivity: DonationActivityModel,
    val createdAt: String,
    val updatedAt: String
)
