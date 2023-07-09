package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class CommonResponseWithNoData(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String
)
