package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class TeamResultResponse(
    @field:SerializedName("TeamName")
    val teamName: String,

    @field:SerializedName("TrashResults")
    val trashResults: Double
)
