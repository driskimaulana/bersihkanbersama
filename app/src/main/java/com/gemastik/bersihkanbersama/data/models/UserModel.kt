package com.gemastik.bersihkanbersama.data.models

data class UserModel(
    val id: String,
    val name: String,
    val phone: String,
    val role: String,
    val email: String,
    val address: AddressModel,
    val points: PointsModel,
    // TODO ACTIVITY
    val createdAt: String,
    val updatedAt: String,
    var token: String = ""
)
