package com.gemastik.bersihkanbersama.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gemastik.bersihkanbersama.data.models.ActivityModel
import com.gemastik.bersihkanbersama.data.models.AllActivityModel
import com.gemastik.bersihkanbersama.data.models.CreateNewActivityModel
import com.gemastik.bersihkanbersama.data.models.LeaderboardModel
import com.gemastik.bersihkanbersama.data.remote.request.TeamResultRequest
import com.gemastik.bersihkanbersama.data.remote.response.CommonResponse
import com.gemastik.bersihkanbersama.data.remote.response.CreateNewActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.GetActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.GetAllActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.LeaderboardResponse
import com.gemastik.bersihkanbersama.data.remote.response.UpdateActivityResponse
import com.gemastik.bersihkanbersama.data.remote.retrofit.ApiService
import com.gemastik.bersihkanbersama.utils.DataMapper
import com.gemastik.bersihkanbersama.utils.Result
import com.google.gson.Gson
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
    private val getAllActivityResult = MediatorLiveData<Result<AllActivityModel>>()
    private val getActivityByIdResult = MediatorLiveData<Result<ActivityModel>>()
    private val startActivityResult = MediatorLiveData<Result<ActivityModel>>()
    private val finishActivityResult = MediatorLiveData<Result<ActivityModel>>()
    private val registerToActivityResult = MediatorLiveData<Result<ActivityModel>>()
    private val addTeamResultsResult = MediatorLiveData<Result<ActivityModel>>()
    private val leaderboardResult = MediatorLiveData<Result<LeaderboardModel>>()

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

    fun getAllActivity(): LiveData<Result<AllActivityModel>> {
        getAllActivityResult.value = Result.Loading

        val client = apiService.getAllActivity()
        client.enqueue(object : Callback<CommonResponse<GetAllActivityResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<GetAllActivityResponse>>,
                response: Response<CommonResponse<GetAllActivityResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 200) {
                        val data = DataMapper.mapAllActivityResponseToAllActivityModel(responseBody.data)
                        getAllActivityResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        getAllActivityResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    getAllActivityResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(
                call: Call<CommonResponse<GetAllActivityResponse>>,
                t: Throwable
            ) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                getAllActivityResult.value = Result.Error(t.message.toString())
            }
        })

        return getAllActivityResult
    }

    fun getActivityById(id: String): LiveData<Result<ActivityModel>> {
        getActivityByIdResult.value = Result.Loading

        val client = apiService.getActivityById(id)
        client.enqueue(object : Callback<CommonResponse<GetActivityResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<GetActivityResponse>>,
                response: Response<CommonResponse<GetActivityResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 200) {
                        val data = DataMapper.mapActivityResponseToActivityModel(responseBody.data.activity)
                        getActivityByIdResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        getActivityByIdResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    getActivityByIdResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<CommonResponse<GetActivityResponse>>, t: Throwable) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                getActivityByIdResult.value = Result.Error(t.message.toString())
            }
        })

        return getActivityByIdResult
    }

    fun startActivity(
        token: String,
        id: String
    ): LiveData<Result<ActivityModel>> {
        startActivityResult.value = Result.Loading

        val client = apiService.startActivity("Bearer $token", id)
        client.enqueue(object : Callback<CommonResponse<UpdateActivityResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<UpdateActivityResponse>>,
                response: Response<CommonResponse<UpdateActivityResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 200) {
                        val data = DataMapper.mapActivityResponseToActivityModel(responseBody.data.updatedActivity)
                        startActivityResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        startActivityResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    startActivityResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(
                call: Call<CommonResponse<UpdateActivityResponse>>,
                t: Throwable
            ) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                startActivityResult.value = Result.Error(t.message.toString())
            }
        })

        return startActivityResult
    }

    fun finishActivity(
        token: String,
        id: String
    ): LiveData<Result<ActivityModel>> {
        finishActivityResult.value = Result.Loading

        val client = apiService.finishActivity("Bearer $token", id)
        client.enqueue(object : Callback<CommonResponse<UpdateActivityResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<UpdateActivityResponse>>,
                response: Response<CommonResponse<UpdateActivityResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 200) {
                        val data = DataMapper.mapActivityResponseToActivityModel(responseBody.data.updatedActivity)
                        finishActivityResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        finishActivityResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    finishActivityResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(
                call: Call<CommonResponse<UpdateActivityResponse>>,
                t: Throwable
            ) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                finishActivityResult.value = Result.Error(t.message.toString())
            }
        })

        return finishActivityResult
    }

    fun registerToActivity(
        token: String,
        id: String
    ): LiveData<Result<ActivityModel>> {
        registerToActivityResult.value = Result.Loading

        val client = apiService.registerToActivity("Bearer $token", id)
        client.enqueue(object : Callback<CommonResponse<UpdateActivityResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<UpdateActivityResponse>>,
                response: Response<CommonResponse<UpdateActivityResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 200) {
                        val data = DataMapper.mapActivityResponseToActivityModel(responseBody.data.updatedActivity)
                        registerToActivityResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        registerToActivityResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    registerToActivityResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(
                call: Call<CommonResponse<UpdateActivityResponse>>,
                t: Throwable
            ) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                registerToActivityResult.value = Result.Error(t.message.toString())
            }
        })

        return registerToActivityResult
    }

    fun addTeamResults(
        token: String,
        id: String,
        teamName: String,
        result: Double
    ): LiveData<Result<ActivityModel>> {
        addTeamResultsResult.value = Result.Loading
        Log.d("driskidebug", token)
        Log.d("driskidebug", id)
        Log.d("driskidebug", teamName)
        Log.d("driskidebug", result.toString())
        val req = TeamResultRequest(teamName, result)

        val client = apiService.addTeamResults("Bearer $token", id, req)
        client.enqueue(object : Callback<CommonResponse<UpdateActivityResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<UpdateActivityResponse>>,
                response: Response<CommonResponse<UpdateActivityResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 200) {
                        val json = Gson().toJson(responseBody.data.updatedActivity)
                        Log.d("DEBUGNOVAL", json)
                        val data = DataMapper.mapActivityResponseToActivityModel(responseBody.data.updatedActivity)
                        addTeamResultsResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        addTeamResultsResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    addTeamResultsResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(
                call: Call<CommonResponse<UpdateActivityResponse>>,
                t: Throwable
            ) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                addTeamResultsResult.value = Result.Error(t.message.toString())
            }
        })

        return addTeamResultsResult
    }

    fun getLeaderboard(id: String): LiveData<Result<LeaderboardModel>> {
        leaderboardResult.value = Result.Loading

        val client = apiService.leaderboard(id)
        client.enqueue(object : Callback<CommonResponse<LeaderboardResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<LeaderboardResponse>>,
                response: Response<CommonResponse<LeaderboardResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 200) {
                        val data = DataMapper.mapLeaderboardResponseToLeaderboardModel(responseBody.data)
                        leaderboardResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        leaderboardResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    leaderboardResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<CommonResponse<LeaderboardResponse>>, t: Throwable) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                leaderboardResult.value = Result.Error(t.message.toString())
            }
        })

        return leaderboardResult
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