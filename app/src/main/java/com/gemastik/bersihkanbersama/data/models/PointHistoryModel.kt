package com.gemastik.bersihkanbersama.data.models

data class PointHistoryModel(
    val title: String,
    val productName: String,
    val pointOut: Int,
    val pointIn: Int,
    val createdAt: String,
    val updatedAt: String
)