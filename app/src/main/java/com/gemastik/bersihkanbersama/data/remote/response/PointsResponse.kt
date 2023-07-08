package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class PointsResponse(
    @field:SerializedName("TotalPoints")
    val totalPoints: Int,

    @field:SerializedName("PointHistory")
    val pointHistory: List<PointHistoryResponse>
)