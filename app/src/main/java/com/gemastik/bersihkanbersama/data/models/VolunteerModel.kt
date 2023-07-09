package com.gemastik.bersihkanbersama.data.models

import com.gemastik.bersihkanbersama.data.remote.response.UserRegisteredResponse

data class VolunteerModel(
    val count: Int,
    val userRegistered: List<UserRegisteredResponse>,
    val teams: List<TeamModel>
)