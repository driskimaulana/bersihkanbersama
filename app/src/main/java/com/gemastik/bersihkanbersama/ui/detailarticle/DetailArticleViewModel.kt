package com.gemastik.bersihkanbersama.ui.detailarticle

import androidx.lifecycle.ViewModel
import com.gemastik.bersihkanbersama.data.repositories.ArticleRepository

class DetailArticleViewModel(
    private val articleRepository: ArticleRepository
) : ViewModel() {
    fun getArticleById(id: String) = articleRepository.getArticleById(id)
}