package com.rdktechnologies.skit.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.widget.Toast
import com.google.gson.Gson
import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.ProfileData


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
    fun setLoginResponse(loginResponse:LoginResponse){
        val prefsEditor= sRef.edit()
        val strValue=Gson().toJson(loginResponse)
        prefsEditor.putString(Constants.SHARED_PREFERENCE_LOGIN_RESPONSE,strValue);
        prefsEditor.apply()
    }
    fun clearLoginResponse(){
        val prefsEditor= sRef.edit()
        prefsEditor.remove(Constants.SHARED_PREFERENCE_LOGIN_RESPONSE)
        prefsEditor.apply()
    }

    fun getLoginResponse(): LoginResponse? {
        val json=sRef.getString(Constants.SHARED_PREFERENCE_LOGIN_RESPONSE, "")
        if(json.equals(""))
            return null
        return Gson().fromJson(json, LoginResponse::class.java)
    }
    fun setToken(token:String){
        val json=sRef.getString(Constants.SHARED_PREFERENCE_LOGIN_RESPONSE, "")
        val obj= Gson().fromJson(json, LoginResponse::class.java)
           obj.token=token
        val prefsEditor= sRef.edit()
        val strValue=Gson().toJson(obj)
        prefsEditor.putString(Constants.SHARED_PREFERENCE_LOGIN_RESPONSE,strValue);
        prefsEditor.apply()
    }
    fun getToken():String?{
        val json=sRef.getString(Constants.SHARED_PREFERENCE_LOGIN_RESPONSE, "")
        if(json.equals(""))
            return null
        val obj=Gson().fromJson(json, LoginResponse::class.java)
        return obj.token
    }
    fun setProfile(profile:ProfileData){
        val json=sRef.getString(Constants.SHARED_PREFERENCE_LOGIN_RESPONSE, "")
        val obj= Gson().fromJson(json, LoginResponse::class.java)
        val prefsEditor= sRef.edit()
        obj.data=profile
        val strValue=Gson().toJson(obj)
        prefsEditor.putString(Constants.SHARED_PREFERENCE_LOGIN_RESPONSE,strValue);
        prefsEditor.apply()
    }
    fun getProfile():ProfileData?{
        val json=sRef.getString(Constants.SHARED_PREFERENCE_LOGIN_RESPONSE, "")
        if(json.equals(""))
            return null
        val obj=Gson().fromJson(json, LoginResponse::class.java)
        return obj.data
    }

}