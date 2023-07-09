package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @field:SerializedName("Name")
    val name: String,

    @field:SerializedName("Members")
    val members: List<UserRegisteredResponse>,

    @field:SerializedName("TrashResults")
    val trashResults: Double
)
