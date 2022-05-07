package com.technicalrupu.sportsapp.HelperClasses.Interface

import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.SignupResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.request.GoogleLoginRequest
import com.rdktechnologies.skit.helperclasses.apiclasses.request.LoginRequest
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
    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest
    ): Call<LoginResponse>
    @POST("auth/google_login")
    fun googleLogin(@Body googleLoginRequest: GoogleLoginRequest
    ): Call<SignupResponse>
}