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
class UdemyTokenInterceptor() :Interceptor {

    override fun intercept(chain: Interceptor.Chain):Response = chain.run {

        proceed(
            request()
                .newBuilder()
                .addHeader("Accept","application/json, text/plain, */*")
                .addHeader("Content-Type","application/json;charset=utf-8")
                .addHeader("Authorization","Basic WnFITUloWVk2czNpcXhOZmExM3VNY3BQbzhGem5pQTk3NTNiRktvMDpMeW51dzVLQkh2bDVJSHNORjRPRVlpeHZQMHVnRVBJUEMxajduR3ZjYUxLVkR1OGhYTmczN2VrVnJwck13OG5zYm5DbXQzeXJSYTJwSkpHc2ZHV2ZKUGdOWng5dU83NmdDaU5qeGdIa0FvNXg3QTE1NUJmZURheWtLTzFuYnBYTA==")
                .build()
        )
    }
}
