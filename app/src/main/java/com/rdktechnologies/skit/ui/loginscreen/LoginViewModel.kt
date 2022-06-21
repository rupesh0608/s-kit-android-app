package com.rdktechnologies.skit.ui.loginscreen

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.rdktechnologies.skit.helperclasses.api.repositories.UserRepository
import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.request.GoogleLoginRequest
import com.rdktechnologies.skit.helperclasses.apiclasses.request.LoginRequest
import com.rdktechnologies.skit.utils.SharedPreference
import com.technicalrupu.sportsapp.HelperClasses.Api.MyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    var firstName: String? = null
    var lastName: String? = null
    var picUrl:String?=null
    var email: String? = null
    var password: String? = null
    var listener: LoginListener? = null
    var context: Context? = null

    fun onLoginButtonClick(view: View) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            listener?.onError("Invalid email or password")
            return
        }
        listener?.showProgress()
        MyApi().login(
            LoginRequest(
                email = email,
                password = password
            )
        ).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful && response.body()!=null) {
                    if(response.body()!!.error!=true) {
                        listener?.onSuccess(response = response.body() as LoginResponse)
                    }else{
                        listener?.onError(response.body()!!.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
               listener?.onError(t.message!!)
            }
        })
    }
    fun onForgotPasswordClick(view: View) {
        listener?.goToForgotPasswordScreen()
    }
    fun onSignUpClick(view: View) {
        listener?.goToSignUpScreen()
    }
    fun onGoogleLoginClick(view: View) {

        listener?.onStarted()
        listener?.googleLogin()
        listener?.showProgress()

    }
    fun googleLoginApiCall(){
        val gooLoginResponse =
            MyApi().googleLogin(
                GoogleLoginRequest(
                    firstName = firstName!!,
                    lastName = lastName!!,
                    email = email!!,
                    pic_url = picUrl!!
                )
            ).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful && response.body()!=null) {
                        if(response.body()!!.error!=true) {
                            listener?.onSuccess(response = response.body() as LoginResponse)
                        }else{
                            listener?.onError(response.body()!!.message!!)
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    listener?.onError(t.message!!)
                }
            })
    }

}