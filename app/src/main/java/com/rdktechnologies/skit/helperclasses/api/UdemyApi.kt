package com.technicalrupu.sportsapp.HelperClasses.Api

import com.rdktechnologies.skit.databinding.ItemCoursesBinding
import com.rdktechnologies.skit.helperclasses.apiclasses.*
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


interface UdemyApi {

@GET("courses/?search=job%20guaranteed%20courses%20for%20class%2010th%20student&price=price-free&is_affiliate_agreed=True&instructional_level=beginner")
fun getRecommendedCourses(
): Call<CoursesResponse>
    companion object{

        operator fun invoke():UdemyApi{
            val client =  OkHttpClient.Builder()
                .addInterceptor(UdemyTokenInterceptor())
                .build()
            val retrofit = Retrofit.Builder().client(client)
                .baseUrl(Constants.UDEMY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(UdemyApi::class.java)
        }

    }
}