package com.technicalrupu.sportsapp.HelperClasses

import com.technicalrupu.sportsapp.HelperClasses.Interface.AuthInterface
import com.technicalrupu.sportsapp.HelperClasses.Interface.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit() {


      fun  createWithAuthInterface():AuthInterface{

          val client =  OkHttpClient.Builder()
              .addInterceptor(TokenInterceptor(""))
              .build()
        val retrofit = Retrofit.Builder().client(client)
            .baseUrl("https://300e-2405-201-3018-2028-41de-c516-15e3-8044.ngrok.io/api/app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(AuthInterface::class.java)
    }
}


