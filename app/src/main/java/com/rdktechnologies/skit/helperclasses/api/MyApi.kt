package com.technicalrupu.sportsapp.HelperClasses.Api

import com.rdktechnologies.skit.helperclasses.apiclasses.EligibleJobResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.ServiceResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.SignupResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.request.GoogleLoginRequest
import com.rdktechnologies.skit.helperclasses.apiclasses.request.LoginRequest
import com.rdktechnologies.skit.helperclasses.apiclasses.request.SignupRequest
import com.rdktechnologies.skit.utils.Constants
import retrofit2.Call
import retrofit2.http.*

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

import kotlin.collections.ArrayList


interface MyApi {


//    @Headers("Content-Type:application/json")
    @POST("app/auth/signup")
    fun signup(@Body signupRequest: SignupRequest
    ): Call<SignupResponse>
    @POST("app/auth/login")
    fun login(@Body loginRequest: LoginRequest
    ): Call<LoginResponse>
    @POST("app/auth/google_login")
    fun googleLogin(@Body googleLoginRequest: GoogleLoginRequest
    ): Call<LoginResponse>

    @Multipart
    @POST("app/profile/edit_profile")
    fun editProfileWithImage(
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String,
        @Query("userId") userId: Long,
        @Query("email") email: String,
        @Query("phoneNumber") phoneNumber: Long?,
        @Part profilePic: MultipartBody.Part,
    ): Call<LoginResponse>

    @POST("app/profile/edit_profile")
    fun editProfileWithOutImage(
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String,
        @Query("userId") userId: Long,
        @Query("email") email: String,
        @Query("phoneNumber") phoneNumber: Long?,
    ): Call<LoginResponse>

    @GET("admin/services/all")
    fun getAllServices(): Call<ServiceResponse>
    @GET("app/jobs/all_eligible_jobs/{id}")
    fun getAllEligibleJobs(@Path("id")  userId:Long): Call<EligibleJobResponse>


    companion object{

        operator fun invoke():MyApi{
            val client =  OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor(""))
                .build()
            val retrofit = Retrofit.Builder().client(client)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(MyApi::class.java)
        }

    }
}