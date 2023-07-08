package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetActivityResponse(
    @field:SerializedName("activity")
    val activity: ActivityResponse
)
