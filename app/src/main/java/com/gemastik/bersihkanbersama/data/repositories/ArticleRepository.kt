package com.gemastik.bersihkanbersama.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gemastik.bersihkanbersama.data.models.AllArticleModel
import com.gemastik.bersihkanbersama.data.models.ArticleModel
import com.gemastik.bersihkanbersama.data.remote.response.ArticleResponse
import com.gemastik.bersihkanbersama.data.remote.response.CommonResponse
import com.gemastik.bersihkanbersama.data.remote.response.CreateNewArticleResponse
import com.gemastik.bersihkanbersama.data.remote.response.GetAllArticleResponse
import com.gemastik.bersihkanbersama.data.remote.response.GetArticleResponse
import com.gemastik.bersihkanbersama.data.remote.retrofit.ApiService
import com.gemastik.bersihkanbersama.utils.DataMapper
import com.gemastik.bersihkanbersama.utils.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository private constructor(
    private val apiService: ApiService
) {
    private val createNewArticleResult = MediatorLiveData<Result<String>>()
    private val getAllArticleResult = MediatorLiveData<Result<AllArticleModel>>()
    private val getArticleByIdResult = MediatorLiveData<Result<ArticleModel>>()

    fun createNewArticle(
        title: RequestBody,
        summary: RequestBody,
        write: RequestBody,
        content: RequestBody,
        coverImage: MultipartBody.Part
    ): LiveData<Result<String>> {
        createNewArticleResult.value = Result.Loading

        val client = apiService.createNewArticle(title, summary, write, content, coverImage)
        client.enqueue(object : Callback<CommonResponse<CreateNewArticleResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<CreateNewArticleResponse>>,
                response: Response<CommonResponse<CreateNewArticleResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 201) {
                        val data = responseBody.data.articleId
                        createNewArticleResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        createNewArticleResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    createNewArticleResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(
                call: Call<CommonResponse<CreateNewArticleResponse>>,
                t: Throwable
            ) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                createNewArticleResult.value = Result.Error(t.message.toString())
            }
        })

        return createNewArticleResult
    }

    fun getAllArticle(): LiveData<Result<AllArticleModel>> {
        getAllArticleResult.value = Result.Loading

        val client = apiService.getAllArticle()
        client.enqueue(object : Callback<CommonResponse<GetAllArticleResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<GetAllArticleResponse>>,
                response: Response<CommonResponse<GetAllArticleResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 200) {
                        val data = DataMapper.mapGetAllArticleResponseToAllArticleModel(responseBody.data)
                        getAllArticleResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        getAllArticleResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    getAllArticleResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(
                call: Call<CommonResponse<GetAllArticleResponse>>,
                t: Throwable
            ) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                getAllArticleResult.value = Result.Error(t.message.toString())
            }
        })

        return getAllArticleResult
    }

    fun getArticleById(id: String): LiveData<Result<ArticleModel>> {
        getArticleByIdResult.value = Result.Loading

        val client = apiService.getArticleById(id)
        client.enqueue(object : Callback<CommonResponse<GetArticleResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<GetArticleResponse>>,
                response: Response<CommonResponse<GetArticleResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 200) {
                        val data = DataMapper.mapArticleResponseToArticleModel(responseBody.data.article)
                        getArticleByIdResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        getArticleByIdResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    getArticleByIdResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<CommonResponse<GetArticleResponse>>, t: Throwable) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                getArticleByIdResult.value = Result.Error(t.message.toString())
            }
        })

        return getArticleByIdResult
    }

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ArticleRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(apiService)
            }.also {
                instance = it
            }
    }
}