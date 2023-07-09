package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class RewardsResponse(
    @field:SerializedName("Participation")
    val participation: Int,

    @field:SerializedName("First")
    val first: Int,

    @field:SerializedName("Second")
    val second: Int,

    @field:SerializedName("Third")
    val third: Int
)
