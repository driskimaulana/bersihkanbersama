package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetAllActivityResponse(
    @field:SerializedName("activities")
    val activities: List<ActivityResponse>
)
