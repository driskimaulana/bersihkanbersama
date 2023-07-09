package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class OrganizationSignUpResponse(
    @field:SerializedName("organizationId")
    val organizationId: String,

    @field:SerializedName("token")
    val token: String
)
