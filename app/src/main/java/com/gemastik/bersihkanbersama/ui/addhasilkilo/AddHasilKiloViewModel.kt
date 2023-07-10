package com.gemastik.bersihkanbersama.ui.addhasilkilo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gemastik.bersihkanbersama.data.models.AccountModel
import com.gemastik.bersihkanbersama.data.repositories.ActivityRepository
import com.gemastik.bersihkanbersama.data.repositories.AuthRepository

class AddHasilKiloViewModel(
    private val authRepository: AuthRepository,
    private val activityRepository: ActivityRepository
) : ViewModel() {
    fun addTeamResults(token: String, activityId: String, teamName: String, result: Double) =
        activityRepository.addTeamResults(token, activityId, teamName, result)

    val account: LiveData<AccountModel> = authRepository.getAccount().asLiveData()
}