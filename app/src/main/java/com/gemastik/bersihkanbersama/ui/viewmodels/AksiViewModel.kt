package com.gemastik.bersihkanbersama.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.gemastik.bersihkanbersama.data.repositories.ActivityRepository
import com.gemastik.bersihkanbersama.data.repositories.AuthRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AksiViewModel (
    private val activityRepository: ActivityRepository,
            private val userRepository: AuthRepository
) : ViewModel() {
    fun getAccount() = userRepository.getAccount()

    fun createNewActivity(
        token: String,
        title: RequestBody,
        description: RequestBody,
        eventDate: RequestBody,
        participationReward: RequestBody,
        firstRewards: RequestBody,
        secondRewards: RequestBody,
        thirdRewards: RequestBody,
        coverImage: MultipartBody.Part,
        city: RequestBody,
        fullAddress: RequestBody
    ) = activityRepository.createNewActivity(
        token, title, description, eventDate, participationReward, firstRewards, secondRewards, thirdRewards, coverImage, city, fullAddress
    )
}