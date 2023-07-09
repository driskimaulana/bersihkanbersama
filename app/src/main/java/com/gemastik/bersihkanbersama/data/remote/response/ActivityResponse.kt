package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class ActivityResponse(
    @field:SerializedName("Id")
    val id: String,

    @field:SerializedName("OrganizationId")
    val organizationId: String,

    @field:SerializedName("Title")
    val title: String,

    @field:SerializedName("Description")
    val description: String,

    @field:SerializedName("EventDate")
    val eventDate: String,

    @field:SerializedName("Location")
    val location: LocationResponse,

    @field:SerializedName("CoverImage")
    val coverImage: String,

    @field:SerializedName("Volunteer")
    val volunteer: VolunteerResponse,

    @field:SerializedName("Status")
    val status: String,

    @field:SerializedName("Rewards")
    val rewards: RewardsResponse,

    @field:SerializedName("DonationActivity")
    val donationActivity: DonationActivityResponse,

    @field:SerializedName("CreatedAt")
    val createdAt: String,

    @field:SerializedName("UpdatedAt")
    val updatedAt: String
)
