package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class OrganizationSignInResponse(
    @field:SerializedName("Id")
    val id: String,

    @field:SerializedName("Name")
    val name: String,

    @field:SerializedName("Email")
    val email: String,

    @field:SerializedName("Password")
    val password: String,

    @field:SerializedName("Role")
    val role: String,

    @field:SerializedName("Description")
    val description: String,

    @field:SerializedName("City")
    val city: String,

    @field:SerializedName("Logo")
    val logo: String,

    @field:SerializedName("Instagram")
    val instagram: String,

    @field:SerializedName("Contact")
    val contact: ContactResponse,

    @field:SerializedName("Activities")
    val activities: Any? = null, // TODO

    @field:SerializedName("CreatedAt")
    val createdAt: String,

    @field:SerializedName("UpdatedAt")
    val updatedAt: String
)