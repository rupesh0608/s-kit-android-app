package com.rdktechnologies.skit.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE


class SharedPreference(var context:Context) {

    var sRef= context.getSharedPreferences(Constants.SHARED_PREFERENCE_FILE,MODE_PRIVATE)


    @SuppressLint("CommitPrefEdits")
    fun setGetStartedPageVisited(value:Boolean){
        val prefsEditor= sRef.edit()
        prefsEditor.putBoolean(Constants.SHARED_PREFERENCE_GET_STARTED,value)
        prefsEditor.apply()
    }
    fun isGetStartedPageVisited():Boolean{
      return sRef.getBoolean(Constants.SHARED_PREFERENCE_GET_STARTED,false)
    }

}