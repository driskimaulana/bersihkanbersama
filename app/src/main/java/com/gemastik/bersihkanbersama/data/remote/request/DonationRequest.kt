package com.gemastik.bersihkanbersama.data.remote.request

import com.google.gson.annotations.SerializedName

data class DonationRequest(
    @field:SerializedName("items")
    val items: List<DonationItemRequest>,

    @field:SerializedName("isAnonim")
    val isAnonim: Boolean
)

data class DonationItemRequest(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("count")
    val count: Int,

    @field:SerializedName("price")
    val price: Double
)
