package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @field:SerializedName("City")
    val city: String,

    @field:SerializedName("FullAddress")
    val fullAddress: String,

    @field:SerializedName("Latitude")
    val latitude: String,

    @field:SerializedName("Longitude")
    val longitude: String
)