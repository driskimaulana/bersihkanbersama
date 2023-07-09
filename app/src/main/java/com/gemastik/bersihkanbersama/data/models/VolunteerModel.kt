package com.gemastik.bersihkanbersama.data.models

data class VolunteerModel(
    val count: Int,
    val userRegistered: List<UserRegisteredModel>,
    val teams: List<TeamModel>
)