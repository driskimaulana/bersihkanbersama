package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserSignInResponse(
    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("user")
    val user: UserResponse
)
