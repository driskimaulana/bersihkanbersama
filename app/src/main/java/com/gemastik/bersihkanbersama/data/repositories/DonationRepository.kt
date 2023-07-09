package com.gemastik.bersihkanbersama.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gemastik.bersihkanbersama.data.models.PaymentDetailsModel
import com.gemastik.bersihkanbersama.data.remote.request.DonationRequest
import com.gemastik.bersihkanbersama.data.remote.response.CommonResponse
import com.gemastik.bersihkanbersama.data.remote.response.CommonResponseWithNoData
import com.gemastik.bersihkanbersama.data.remote.response.CreateNewDonationResponse
import com.gemastik.bersihkanbersama.data.remote.response.PaymentDetailsResponse
import com.gemastik.bersihkanbersama.data.remote.retrofit.ApiService
import com.gemastik.bersihkanbersama.utils.DataMapper
import com.gemastik.bersihkanbersama.utils.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/***
 * repository for donation data
 */
class DonationRepository private constructor(
    private val apiService: ApiService
){
    private val createNewDonationResult = MediatorLiveData<Result<String>>()
    private val getPaymentDetailsResult = MediatorLiveData<Result<PaymentDetailsModel>>()
    private val paidWebhooksResult = MediatorLiveData<Result<String>>()

    fun createNewDonation(
        token: String,
        id: String,
        requestBody: DonationRequest
    ): LiveData<Result<String>> {
        createNewDonationResult.value = Result.Loading

        val client = apiService.createNewDonation(token, id, requestBody)
        client.enqueue(object : Callback<CommonResponse<CreateNewDonationResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<CreateNewDonationResponse>>,
                response: Response<CommonResponse<CreateNewDonationResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 201) {
                        val data = responseBody.data.donationId
                        createNewDonationResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        createNewDonationResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    createNewDonationResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(
                call: Call<CommonResponse<CreateNewDonationResponse>>,
                t: Throwable
            ) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                createNewDonationResult.value = Result.Error(t.message.toString())
            }
        })

        return createNewDonationResult
    }

    fun getPaymentDetails(id: String): LiveData<Result<PaymentDetailsModel>> {
        getPaymentDetailsResult.value = Result.Loading

        val client = apiService.getPaymentDetails(id)
        client.enqueue(object : Callback<CommonResponse<PaymentDetailsResponse>> {
            override fun onResponse(
                call: Call<CommonResponse<PaymentDetailsResponse>>,
                response: Response<CommonResponse<PaymentDetailsResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 200) {
                        val data = DataMapper.mapPaymentDetailsResponseToPaymentDetailsModel(responseBody.data)
                        getPaymentDetailsResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        getPaymentDetailsResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    getPaymentDetailsResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(
                call: Call<CommonResponse<PaymentDetailsResponse>>,
                t: Throwable
            ) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                getPaymentDetailsResult.value = Result.Error(t.message.toString())
            }

        })

        return getPaymentDetailsResult
    }

    fun paidWebhooks(
        id: String,
        status: String
    ): LiveData<Result<String>> {
        paidWebhooksResult.value = Result.Loading

        val client = apiService.paidWebhooks(id, status)
        client.enqueue(object : Callback<CommonResponseWithNoData> {
            override fun onResponse(
                call: Call<CommonResponseWithNoData>,
                response: Response<CommonResponseWithNoData>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == 200) {
                        val data = responseBody.message
                        paidWebhooksResult.value = Result.Success(data)
                    } else {
                        Log.e("ERROR", "onResponse: ${responseBody.message}")
                        paidWebhooksResult.value = Result.Error(responseBody.message)
                    }
                } else {
                    Log.e("ERROR", "onResponse: ${response.message()}")
                    paidWebhooksResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<CommonResponseWithNoData>, t: Throwable) {
                Log.e("ERROR", "onFailure: ${t.message.toString()}")
                paidWebhooksResult.value = Result.Error(t.message.toString())
            }
        })

        return paidWebhooksResult
    }

    // make this class singleton
    companion object {
        @Volatile
        private var instance: DonationRepository? = null
        fun getInstance(
            apiService: ApiService
        ): DonationRepository =
            instance ?: synchronized(this) {
                instance ?: DonationRepository(apiService)
            }.also {
                instance = it
            }
    }
}