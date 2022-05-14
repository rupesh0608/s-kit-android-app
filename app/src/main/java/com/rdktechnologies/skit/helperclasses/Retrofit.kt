package com.technicalrupu.sportsapp.HelperClasses

import android.content.Context
import com.rdktechnologies.skit.utils.Constants
import com.rdktechnologies.skit.utils.SharedPreference
import com.technicalrupu.sportsapp.HelperClasses.Api.MyApi
import com.technicalrupu.sportsapp.HelperClasses.Api.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit(var context: Context) {


      fun  createWithAuthInterface(): MyApi {

          val client =  OkHttpClient.Builder()
              .addInterceptor(TokenInterceptor(SharedPreference(context).getToken()!!))
              .build()
        val retrofit = Retrofit.Builder().client(client)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MyApi::class.java)
    }
    fun  createWithOutInterface():MyApi{

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


