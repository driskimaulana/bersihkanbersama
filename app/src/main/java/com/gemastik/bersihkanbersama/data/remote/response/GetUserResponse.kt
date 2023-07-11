package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetUserResponse(
    @field:SerializedName("user")
    val user: UserResponse
)
