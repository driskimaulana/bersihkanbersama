package com.gemastik.bersihkanbersama.ui.aksiberlangsung

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gemastik.bersihkanbersama.data.repositories.ActivityRepository
import com.gemastik.bersihkanbersama.data.repositories.AuthRepository

class AksiBerlangsungViewModel(
    private val authRepository: AuthRepository,
    private val activityRepository: ActivityRepository
) : ViewModel() {
    fun getAccount() = authRepository.getAccount().asLiveData()

    fun getLeaderboard(id: String) = activityRepository.getLeaderboard(id)

    fun getActivityById(id: String) = activityRepository.getActivityById(id)

    fun finishActivity(token: String, id: String) = activityRepository.finishActivity(token, id)
}