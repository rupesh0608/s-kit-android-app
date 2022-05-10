package com.rdktechnologies.skit.ui.loginscreen

import android.view.View
import androidx.lifecycle.ViewModel
import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.SignupResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.request.GoogleLoginRequest
import com.rdktechnologies.skit.helperclasses.apiclasses.request.LoginRequest
import com.technicalrupu.sportsapp.HelperClasses.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    var email: String? = null
    var password: String? = null
    var listener: LoginListener? = null


    fun onLoginButtonClick(view: View) {
        Retrofit().createWithAuthInterface().login(
            LoginRequest(
                email = email,
                password = password
            )
        ).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {

                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body() as LoginResponse
                    if (loginResponse.error!!) {
                        listener?.onError(loginResponse.message!!)
                    } else {
                        listener?.onSuccess(loginResponse)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                listener?.onError(t.message.toString())
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
    }

    fun doGoogleLogin(
        firstName: String?,
        lastName: String?,
        email: String?,
        picUrl: String?
    ) {
        val retrofit = Retrofit()

        retrofit.createWithAuthInterface().googleLogin(
            GoogleLoginRequest(
                firstName = firstName.toString(),
                lastName = lastName.toString(),
                email = email.toString(),
                pic_url = picUrl.toString()
            )
        ).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {

                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body() as LoginResponse
                    if (loginResponse.error!!) {
                        listener?.onError(loginResponse.message!!)
                    } else {
                        listener?.onSuccess(loginResponse)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                listener?.onError(t.message.toString())
            }
        })
    }
}