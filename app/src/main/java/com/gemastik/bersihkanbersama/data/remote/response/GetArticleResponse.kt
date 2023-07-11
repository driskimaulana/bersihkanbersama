package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetArticleResponse(
    @field:SerializedName("article")
    val article: ArticleResponse
)
