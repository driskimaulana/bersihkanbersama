package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserRegisteredResponse(
    @field:SerializedName("Id")
    val id: String,

    @field:SerializedName("Name")
    val name: String,

    @field:SerializedName("Phone")
    val phone: String
)
