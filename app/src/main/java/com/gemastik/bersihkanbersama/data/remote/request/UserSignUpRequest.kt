package com.gemastik.bersihkanbersama.data.remote.request

data class UserSignUpRequest(
    val name: String,
    val phone: String,
    val email: String,
    val password: String
)
