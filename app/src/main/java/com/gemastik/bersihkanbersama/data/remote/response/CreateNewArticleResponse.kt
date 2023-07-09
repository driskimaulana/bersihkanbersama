package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class CreateNewArticleResponse(
    @field:SerializedName("articleId")
    val articleId: String
)
