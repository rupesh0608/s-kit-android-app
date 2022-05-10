package com.rdktechnologies.skit.ui.splashscreen

interface SplashListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure()
    fun onFirstTimeVisit()
    fun startHandler()
}