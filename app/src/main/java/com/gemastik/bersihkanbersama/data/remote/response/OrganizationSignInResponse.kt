package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class OrganizationSignInResponse(
    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("organization")
    val organization: OrganizationResponse
)