package com.rdktechnologies.skit.ui.profilescreen.subactivity.editprofile

import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse

interface EditProfileListener {

    fun onStarted()
    fun onFailure(message: String)
    fun onSuccess(message: LoginResponse)
    fun selectImageFromGallery()
    fun showProgress()
}