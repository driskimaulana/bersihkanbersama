package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class CreateNewActivityResponse(
    @SerializedName("activityI")
    val activityId: String
)
