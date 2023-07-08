package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class PointHistoryResponse(
    @field:SerializedName("Title")
    val title: String,

    @field:SerializedName("ProductName")
    val productName: String,

    @field:SerializedName("PointOut")
    val pointOut: Int,

    @field:SerializedName("PointIn")
    val pointIn: Int,

    @field:SerializedName("CreatedAt")
    val createdAt: String,

    @field:SerializedName("UpdatedAt")
    val updatedAt: String
)