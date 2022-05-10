package com.rdktechnologies.skit.ui.signupscreen

import android.view.View
import androidx.lifecycle.ViewModel
import com.rdktechnologies.skit.helperclasses.apiclasses.SignupResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.request.SignupRequest
import com.technicalrupu.sportsapp.HelperClasses.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    var firstName: String? = ""
    var lastName: String? = ""
    var email: String? = ""
    var password: String? = ""
    var confirmPassword: String? = ""
    var listener: SignUpListener? = null

    fun onSignUpClick(view: View) {
        listener?.onStarted()
        val signupRequest =
            SignupRequest(firstName!!, lastName!!, email!!, password!!, confirmPassword!!)
        Retrofit().createWithAuthInterface().signup(
            signupRequest
        ).enqueue(object : Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {

                if (response.isSuccessful && response.body() != null) {
                    val signupResponse = response.body() as SignupResponse
                    if (signupResponse.error) {
                        listener?.onError(signupResponse.message)
                    } else {
                        listener?.onSuccess(signupResponse)
                    }
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                listener?.onError(t.message.toString())
            }
        })
    }
    fun onLoginNowClick(view: View){
        listener?.goToLoginScreen()
    }
}