package com.rdktechnologies.skit.ui.splashscreen

import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse

interface SplashListener {
    fun onStarted()
    fun onSuccess(loginResponse: LoginResponse?)
    fun onFailure()
    fun onFirstTimeVisit()
    fun startHandler()
    fun checkPermissions()
}