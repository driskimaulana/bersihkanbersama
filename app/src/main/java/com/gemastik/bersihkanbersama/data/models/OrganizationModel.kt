package com.gemastik.bersihkanbersama.data.models

data class OrganizationModel(
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val description: String,
    val city: String,
    val logo: String,
    val instagram: String,
    val contact: ContactModel,
    // TODO ACTIVITY
    val createdAt: String,
    val updatedAt: String,
    var token: String = ""
)
