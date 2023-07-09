package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateActivityResponse(
    @field:SerializedName("updatedActivity")
    val updatedActivity: ActivityResponse
)
