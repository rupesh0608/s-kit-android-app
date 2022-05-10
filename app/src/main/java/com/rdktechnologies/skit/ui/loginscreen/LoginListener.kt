package com.rdktechnologies.skit.ui.loginscreen

import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse

interface LoginListener {
   fun onStarted()
   fun onSuccess(response: LoginResponse)
   fun onError(message:String)
   fun goToSignUpScreen()
   fun googleLogin()
   fun goToForgotPasswordScreen()
}