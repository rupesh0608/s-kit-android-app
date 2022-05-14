package com.technicalrupu.sportsapp.HelperClasses.Api

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(var token:String) :Interceptor {

    override fun intercept(chain: Interceptor.Chain):Response = chain.run {

        proceed(
            request()
                .newBuilder()
                .addHeader("Content-Type","application/json").build()
        )
    }
}
