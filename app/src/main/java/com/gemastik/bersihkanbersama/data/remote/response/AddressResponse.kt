package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @field:SerializedName("Phone")
    val phone: String,

    @field:SerializedName("Province")
    val province: String,

    @field:SerializedName("SubDistrict")
    val subDistrict: String,

    @field:SerializedName("City")
    val city: String,

    @field:SerializedName("FullAddress")
    val fullAddress: String,

    @field:SerializedName("PostalCode")
    val postalCode: String
)