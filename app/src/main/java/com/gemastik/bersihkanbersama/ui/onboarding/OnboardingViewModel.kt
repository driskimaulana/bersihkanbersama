package com.gemastik.bersihkanbersama.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gemastik.bersihkanbersama.data.repositories.AuthRepository

class OnboardingViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun getAccount() = authRepository.getAccount().asLiveData()
}