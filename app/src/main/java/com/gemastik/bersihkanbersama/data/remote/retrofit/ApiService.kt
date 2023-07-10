package com.gemastik.bersihkanbersama.data.remote.retrofit

import com.gemastik.bersihkanbersama.data.remote.request.DonationRequest
import com.gemastik.bersihkanbersama.data.remote.request.OrganizationSignUpRequest
import com.gemastik.bersihkanbersama.data.remote.request.SignInRequest
import com.gemastik.bersihkanbersama.data.remote.response.ArticleResponse
import com.gemastik.bersihkanbersama.data.remote.response.CommonResponse
import com.gemastik.bersihkanbersama.data.remote.response.CommonResponseWithNoData
import com.gemastik.bersihkanbersama.data.remote.response.CreateNewActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.CreateNewArticleResponse
import com.gemastik.bersihkanbersama.data.remote.response.CreateNewDonationResponse
import com.gemastik.bersihkanbersama.data.remote.response.GetActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.GetAllActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.GetAllArticleResponse
import com.gemastik.bersihkanbersama.data.remote.response.GetArticleResponse
import com.gemastik.bersihkanbersama.data.remote.response.GetUserResponse
import com.gemastik.bersihkanbersama.data.remote.response.LeaderboardResponse
import com.gemastik.bersihkanbersama.data.remote.response.OrganizationSignInResponse
import com.gemastik.bersihkanbersama.data.remote.response.OrganizationSignUpResponse
import com.gemastik.bersihkanbersama.data.remote.response.PaymentDetailsResponse
import com.gemastik.bersihkanbersama.data.remote.response.UpdateActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.UserSignInResponse
import com.gemastik.bersihkanbersama.data.remote.response.UserSignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    fun getUser(
        @Header("Authorization") token: String
    ): Call<CommonResponse<GetUserResponse>>

    @POST("user/signin")
    fun userSignIn(
        @Body signInRequest: SignInRequest
    ): Call<CommonResponse<UserSignInResponse>>

    @POST("organization/signin")
    fun organizationSignIn(
        @Body signInRequest: SignInRequest
    ): Call<CommonResponse<OrganizationSignInResponse>>

    @FormUrlEncoded
    @POST("user/signup")
    fun userSignUp(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<CommonResponse<UserSignUpResponse>>

    @POST("organization/signup")
    fun organizationSignUp(
        @Body request: OrganizationSignUpRequest
    ): Call<CommonResponse<OrganizationSignUpResponse>>

    @Multipart
    @POST("activity")
    fun createNewActivity(
        @Header("Authorization") token: String,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("eventDate") eventDate: RequestBody,
        @Part("participationRewards") participationRewards: RequestBody,
        @Part("firstRewards") firstRewards: RequestBody,
        @Part("secondRewards") secondRewards: RequestBody,
        @Part("thirdRewards") thirdRewards: RequestBody,
        @Part("coverImage") coverImage: MultipartBody.Part,
        @Part("city") city: RequestBody,
        @Part("fullAddress") fullAddress: RequestBody
    ): Call<CommonResponse<CreateNewActivityResponse>>

    @GET("activity")
    fun getAllActivity(): Call<CommonResponse<GetAllActivityResponse>>

    @GET("activity/{id}")
    fun getActivityById(
        @Path("id") id: String
    ): Call<CommonResponse<GetActivityResponse>>

    @PUT("activity/start/{id}")
    fun startActivity(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<CommonResponse<UpdateActivityResponse>>

    @PUT("activity/finish/{id}")
    fun finishActivity(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<CommonResponse<UpdateActivityResponse>>

    @PUT("activity/register/{id}")
    fun registerToActivity(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<CommonResponse<UpdateActivityResponse>>

    @PUT("activity/teamresults/{id}")
    fun addTeamResults(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<CommonResponse<UpdateActivityResponse>>

    @GET("activity/leaderboard/{id}")
    fun leaderboard(
        @Path("id") id: String
    ): Call<CommonResponse<LeaderboardResponse>>

    @POST("activity/donate/{id}")
    fun createNewDonation(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body requestBody: DonationRequest
    ): Call<CommonResponse<CreateNewDonationResponse>>

    @GET("activity/donate/details/{id}")
    fun getPaymentDetails(
        @Path("id") id: String
    ): Call<CommonResponse<PaymentDetailsResponse>>

    @FormUrlEncoded
    @POST("webhooks/xendit")
    fun paidWebhooks(
        @Field("id") id: String,
        @Field("status") status: String
    ): Call<CommonResponseWithNoData>

    @Multipart
    @POST("article")
    fun createNewArticle(
        @Part("title") title: RequestBody,
        @Part("summary") summary: RequestBody,
        @Part("writer") writer: RequestBody,
        @Part("content") content: RequestBody,
        @Part("coverImage") coverImage: MultipartBody.Part
    ): Call<CommonResponse<CreateNewArticleResponse>>

    @GET("article")
    fun getAllArticle(): Call<CommonResponse<GetAllArticleResponse>>

    @GET("article/{id}")
    fun getArticleById(
        @Path("id") id: String
    ): Call<CommonResponse<GetArticleResponse>>
}