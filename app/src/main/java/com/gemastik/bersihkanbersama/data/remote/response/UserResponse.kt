package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("Id")
    val id: String,

    @field:SerializedName("Name")
    val name: String,

    @field:SerializedName("Phone")
    val phone: String,

    @field:SerializedName("Role")
    val role: String,

    @field:SerializedName("Email")
    val email: String,

    @field:SerializedName("Password")
    val password: String,

    @field:SerializedName("Address")
    val address: AddressResponse,

    @field:SerializedName("Points")
    val points: PointsResponse,

    @field:SerializedName("Activity")
    val activities: List<String>? = null,

    @field:SerializedName("CreatedAt")
    val createdAt: String,

    @field:SerializedName("UpdatedAt")
    val updatedAt: String
)