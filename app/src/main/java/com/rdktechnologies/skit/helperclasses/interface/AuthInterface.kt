package com.technicalrupu.sportsapp.HelperClasses.Interface

import com.rdktechnologies.skit.helperclasses.apiclasses.SignupResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.request.SignupRequest
import retrofit2.Call
import retrofit2.http.*

import okhttp3.MultipartBody
import okhttp3.RequestBody

import kotlin.collections.ArrayList


interface AuthInterface {


//    @Headers("Content-Type:application/json")
    @POST("auth/signup")
    fun signup(@Body signupRequest: SignupRequest
    ): Call<SignupResponse>
//
//    @POST("authentication/login")
//    @FormUrlEncoded
//    fun login(
//        @Field("email") email: String,
//        @Field("password") password: String,
//        @Field("device_token") device_token: String
//    ): Call<UserLogin>
//
//    @POST("authentication/forgot_password")
//    @FormUrlEncoded
//    fun forgotPassword(
//        @Field("email") email: String,
//    ): Call<CommonResponse>
//
//    @POST("authentication/reset_password")
//    @FormUrlEncoded
//    fun resetPassword(
//        @Field("id") id: Int,
//        @Field("old_password") oldPassword: String,
//        @Field("new_password") newPassword: String,
//        @Field("confirm_password") confirmPassword: String,
//
//        ): Call<CommonResponse>
//
//    @POST("UserFeed/view_userfeed")
//    @FormUrlEncoded
//    fun viewUserFeed(
//        @Field("user_id") user_id: Int,
//    ): Call<UserFeed>
//
//    @Multipart
//    @POST("UserFeed/add_userfeed")
//     fun addNewPost(
//        @Part("user_id") user_id: RequestBody,
//        @Part("description") description: RequestBody,
//        @Part image: MultipartBody.Part,
//        @Part("hashtag[]") hashtag: ArrayList<String>,
//    ): Call<ImageResponse>
}