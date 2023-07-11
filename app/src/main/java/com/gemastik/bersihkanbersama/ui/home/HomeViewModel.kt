package com.gemastik.bersihkanbersama.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gemastik.bersihkanbersama.data.repositories.ActivityRepository
import com.gemastik.bersihkanbersama.data.repositories.ArticleRepository
import com.gemastik.bersihkanbersama.data.repositories.AuthRepository

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val activityRepository: ActivityRepository,
    private val articleRepository: ArticleRepository
) : ViewModel() {
    fun getUser(token: String) = authRepository.getUser(token)

    fun getAccount() = authRepository.getAccount().asLiveData()

    fun getAllActivity() = activityRepository.getAllActivity()

    fun getAllArticle() = articleRepository.getAllArticle()
}