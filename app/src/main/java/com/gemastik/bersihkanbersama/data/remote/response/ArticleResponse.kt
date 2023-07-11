package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @field:SerializedName("Id")
    val id: String,

    @field:SerializedName("Title")
    val title: String,

    @field:SerializedName("Writer")
    val write: String,

    @field:SerializedName("Summary")
    val summary: String,

    @field:SerializedName("Content")
    val content: String,

    @field:SerializedName("Cover")
    val cover: String,

    @field:SerializedName("CreatedAt")
    val createdAt: String,

    @field:SerializedName("UpdatedAt")
    val updatedAt: String
)