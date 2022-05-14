package com.rdktechnologies.skit.helperclasses.api.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.request.GoogleLoginRequest
import com.rdktechnologies.skit.helperclasses.apiclasses.request.LoginRequest
import com.technicalrupu.sportsapp.HelperClasses.Api.MyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    fun userLogin(email:String,password:String):LoginResponse{
        var loginResponse=LoginResponse()

        return loginResponse
    }

    fun googleLogin(firstName:String,lastName:String,email:String,picUrl:String):LoginResponse{
        var googleLoginResponse=LoginResponse()

        return googleLoginResponse
    }
}