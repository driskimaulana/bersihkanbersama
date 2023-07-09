package com.gemastik.bersihkanbersama.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gemastik.bersihkanbersama.data.models.AllActivityModel
import com.gemastik.bersihkanbersama.data.models.CreateNewActivityModel
import com.gemastik.bersihkanbersama.data.remote.response.CommonResponse
import com.gemastik.bersihkanbersama.data.remote.response.CreateNewActivityResponse
import com.gemastik.bersihkanbersama.data.remote.retrofit.ApiService
import com.gemastik.bersihkanbersama.utils.DataMapper
import com.gemastik.bersihkanbersama.utils.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/***
 * repository for activity data
 */
class ActivityRepository private constructor(
    private val apiService: ApiService
) {
    private val createNewActivityResult = MediatorLiveData<Result<CreateNewActivityModel>>()
    private val getAllActivity = MediatorLiveData<Result<AllActivityModel>>()

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
    ): LiveData<Result<CreateNewActivityModel>> {
        createNewActivityResult.value = Result.Loading

        val client = apiService.createNewActivity(
            token = "Bearer $token",
            title = title,
            description = description,
            eventDate = eventDate,
            participationRewards = participationReward,
            firstRewards = firstRewards,
            secondRewards = secondRewards,
            thirdRewards = thirdRewards,
            coverImage = coverImage,
            city = city,
            fullAddress = fullAddress
        )
        client.enqueue(object : Callback<CommonResponse<CreateNewActivityResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<CreateNewActivityResponse>>,
                response: Response<CommonResponse<CreateNewActivityResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 201) {
                        val data = DataMapper.mapCreateNewActivityResponseToCreateNewActivityModel(responseBody.data)
                        createNewActivityResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        createNewActivityResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    createNewActivityResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(
                call: Call<CommonResponse<CreateNewActivityResponse>>,
                t: Throwable
            ) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                createNewActivityResult.value = Result.Error(t.message.toString())
            }
        })

        return createNewActivityResult
    }

    // Make this class singleton
    companion object {
        @Volatile
        private var instance: ActivityRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ActivityRepository =
            instance ?: synchronized(this) {
                instance ?: ActivityRepository(apiService)
            }.also {
                instance = it
            }
    }
}