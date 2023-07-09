package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserSignUpResponse(
    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("userId")
    val userId: String
)
