package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class VolunteerResponse(
    @field:SerializedName("Count")
    val count: Int,

    @field:SerializedName("UserRegistered")
    val userRegistered: List<UserRegisteredResponse>,

    @field:SerializedName("Teams")
    val teams: List<TeamResponse>
)
