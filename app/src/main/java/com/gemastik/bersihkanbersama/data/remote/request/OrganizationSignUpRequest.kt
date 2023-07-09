package com.gemastik.bersihkanbersama.data.remote.request

import com.google.gson.annotations.SerializedName

data class OrganizationSignUpRequest(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("city")
    val city: String,

    @field:SerializedName("instagram")
    val instagram: String,

    @field:SerializedName("contact")
    val contact: ContactRequest
)

data class ContactRequest(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("phone")
    val phone: String
)