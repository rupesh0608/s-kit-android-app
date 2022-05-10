package com.rdktechnologies.skit.ui.splashscreen

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.rdktechnologies.skit.utils.SharedPreference

class SplashViewModel :ViewModel(){

    var splashListener:SplashListener?=null

    fun getRequiredPermissions(){

    }
    fun startHandler(){
        splashListener?.onStarted()
         splashListener?.startHandler()
    }
}