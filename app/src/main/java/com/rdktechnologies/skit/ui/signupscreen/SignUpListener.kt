package com.rdktechnologies.skit.ui.signupscreen

import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.SignupResponse

interface SignUpListener {
    fun onStarted()
    fun onError(message:String)
    fun onSuccess(response:SignupResponse)
    fun goToLoginScreen()

}