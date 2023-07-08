package com.gemastik.bersihkanbersama.data.remote.retrofit

import com.gemastik.bersihkanbersama.data.remote.response.CommonResponse
import com.gemastik.bersihkanbersama.data.remote.response.CreateNewActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.GetActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.GetAllActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.LeaderboardResponse
import com.gemastik.bersihkanbersama.data.remote.response.OrganizationSignInResponse
import com.gemastik.bersihkanbersama.data.remote.response.OrganizationSignUpResponse
import com.gemastik.bersihkanbersama.data.remote.response.UpdateActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.UserSignInResponse
import com.gemastik.bersihkanbersama.data.remote.response.UserSignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("user/signin")
    fun userSignIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<CommonResponse<UserSignInResponse>>

    @FormUrlEncoded
    @POST("organization/signin")
    fun organizationSignIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<CommonResponse<OrganizationSignInResponse>>

    @FormUrlEncoded
    @POST("user/signup")
    fun userSignUp(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<CommonResponse<UserSignUpResponse>>

    @FormUrlEncoded
    @POST("organization/signup")
    fun organizationSignUp(
        @Field("name") name: String,
        @Field("description") description: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @FieldMap contact: HashMap<String, RequestBody>
    ): Call<CommonResponse<OrganizationSignUpResponse>>

    /* TODO try using request body not form url encoded
    @POST("organization/signup")
    fun organizationSignUpBody(
        @Body request: OrganizationSignUpRequest
    ): Call<CommonResponse<OrganizationSignUpResponse>>
     */

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

    // TODO REGISTER TO ACTIVITY

    @PUT("activity/teamresults/{id}")
    fun addTeamResults(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<CommonResponse<UpdateActivityResponse>>

    @GET("activity/leaderboard/{id}")
    fun leaderboard(
        @Path("id") id: String
    ): Call<CommonResponse<LeaderboardResponse>>
}