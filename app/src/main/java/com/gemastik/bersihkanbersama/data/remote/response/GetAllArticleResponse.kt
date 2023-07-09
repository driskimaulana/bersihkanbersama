package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetAllArticleResponse(
    @field:SerializedName("articles")
    val articles: List<ArticleResponse>
)
