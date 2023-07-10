package com.gemastik.bersihkanbersama.ui.detailaksi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gemastik.bersihkanbersama.data.repositories.ActivityRepository
import com.gemastik.bersihkanbersama.data.repositories.AuthRepository

class DetailAksiViewModel(
    private val authRepository: AuthRepository,
    private val activityRepository: ActivityRepository
) : ViewModel() {
    fun getAccount() = authRepository.getAccount().asLiveData()

    fun getActivityById(id: String) = activityRepository.getActivityById(id)

    fun startActivity(token: String, id: String) = activityRepository.startActivity(token, id)

    fun registerToActivity(token: String, id: String) = activityRepository.registerToActivity(token, id)
}