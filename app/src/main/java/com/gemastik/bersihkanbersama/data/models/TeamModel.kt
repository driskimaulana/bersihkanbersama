package com.gemastik.bersihkanbersama.data.models

data class TeamModel(
    val name: String,
    val members: List<UserRegisteredModel>,
    val trashResults: Double
)
