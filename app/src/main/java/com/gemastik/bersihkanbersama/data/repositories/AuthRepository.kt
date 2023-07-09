package com.gemastik.bersihkanbersama.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gemastik.bersihkanbersama.data.local.AccountPreference
import com.gemastik.bersihkanbersama.data.models.AccountModel
import com.gemastik.bersihkanbersama.data.models.OrganizationModel
import com.gemastik.bersihkanbersama.data.models.OrganizationSignUpModel
import com.gemastik.bersihkanbersama.data.models.UserModel
import com.gemastik.bersihkanbersama.data.models.UserSignUpModel
import com.gemastik.bersihkanbersama.data.remote.response.CommonResponse
import com.gemastik.bersihkanbersama.data.remote.response.OrganizationSignInResponse
import com.gemastik.bersihkanbersama.data.remote.response.OrganizationSignUpResponse
import com.gemastik.bersihkanbersama.data.remote.response.UserSignInResponse
import com.gemastik.bersihkanbersama.data.remote.response.UserSignUpResponse
import com.gemastik.bersihkanbersama.data.remote.retrofit.ApiService
import com.gemastik.bersihkanbersama.utils.DataMapper
import com.gemastik.bersihkanbersama.utils.Result
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/***
 * repository for account information
 */
class AuthRepository private constructor(
    private val apiService: ApiService,
    private val preference: AccountPreference
) {
    private val userSignInResult = MediatorLiveData<Result<UserModel>>()
    private val userSignUpResult = MediatorLiveData<Result<UserSignUpModel>>()
    private val organizationSignInResult = MediatorLiveData<Result<OrganizationModel>>()
    private val organizationSignUpResult = MediatorLiveData<Result<OrganizationSignUpModel>>()

    fun userSignIn(
        email: String,
        password: String
    ): LiveData<Result<UserModel>> {
        userSignInResult.value = Result.Loading

        val client = apiService.userSignIn(email, password)
        client.enqueue(object : Callback<CommonResponse<UserSignInResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<UserSignInResponse>>,
                response: Response<CommonResponse<UserSignInResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 200) {
                        val user = DataMapper.mapUserResponseToUserModel(responseBody.data.user)
                        user.token = responseBody.data.token
                        userSignInResult.value = Result.Success(user)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        userSignInResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    userSignInResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<CommonResponse<UserSignInResponse>>, t: Throwable) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                userSignInResult.value = Result.Error(t.message.toString())
            }
        })

        return userSignInResult
    }

    fun userSignUp(
        name: String,
        phone: String,
        email: String,
        password: String
    ): LiveData<Result<UserSignUpModel>> {
        userSignUpResult.value = Result.Loading

        val client = apiService.userSignUp(name, phone, email, password)
        client.enqueue(object : Callback<CommonResponse<UserSignUpResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<UserSignUpResponse>>,
                response: Response<CommonResponse<UserSignUpResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 201) {
                        val data =
                            DataMapper.mapUserSignUpResponseToUserSignUpModel(responseBody.data)
                        userSignUpResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        userSignUpResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    userSignUpResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<CommonResponse<UserSignUpResponse>>, t: Throwable) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                userSignUpResult.value = Result.Error(t.message.toString())
            }
        })

        return userSignUpResult
    }

    fun organizationSignIn(
        email: String,
        password: String
    ): LiveData<Result<OrganizationModel>> {
        organizationSignInResult.value = Result.Loading

        val client = apiService.organizationSignIn(email, password)
        client.enqueue(object : Callback<CommonResponse<OrganizationSignInResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<OrganizationSignInResponse>>,
                response: Response<CommonResponse<OrganizationSignInResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 200) {
                        val organization =
                            DataMapper.mapOrganizationResponseToOrganizationModel(responseBody.data.organization)
                        organization.token = responseBody.data.token
                        organizationSignInResult.value = Result.Success(organization)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        organizationSignInResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    organizationSignInResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(
                call: Call<CommonResponse<OrganizationSignInResponse>>,
                t: Throwable
            ) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                organizationSignInResult.value = Result.Error(t.message.toString())
            }
        })

        return organizationSignInResult
    }

    fun organizationSignUp(
        name: String,
        description: String,
        email: String,
        password: String,
        contact: HashMap<String, RequestBody>
    ): LiveData<Result<OrganizationSignUpModel>> {
        organizationSignUpResult.value = Result.Loading

        val client = apiService.organizationSignUp(name, description, email, password, contact)
        client.enqueue(object : Callback<CommonResponse<OrganizationSignUpResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<OrganizationSignUpResponse>>,
                response: Response<CommonResponse<OrganizationSignUpResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 201) {
                        val data =
                            DataMapper.mapOrganizationSignUpResponseToOrganizationSignUpModel(responseBody.data)
                        organizationSignUpResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        organizationSignUpResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    organizationSignUpResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(
                call: Call<CommonResponse<OrganizationSignUpResponse>>,
                t: Throwable
            ) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                organizationSignUpResult.value = Result.Error(t.message.toString())
            }
        })

        return organizationSignUpResult
    }

    fun getAccount() = preference.getAccount()

    suspend fun saveAccount(account: AccountModel) {
        preference.saveAccount(account)
    }

    suspend fun deleteAccount() {
        preference.deleteAccount()
    }

    // Make this class singleton
    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            apiService: ApiService,
            preference: AccountPreference
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, preference)
            }.also {
                instance = it
            }
    }
}