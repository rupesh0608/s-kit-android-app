package com.rdktechnologies.skit.ui.profilescreen

import androidx.lifecycle.ViewModel
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.ui.splashscreen.SplashListener
import com.rdktechnologies.skit.utils.SharedPreference

class ProfileViewModel:ViewModel(){
    var profileListener: ProfileListener?=null
    fun onStarted(){
        profileListener?.setDetails()
        val data = ArrayList<ProfileButtonModel>()
        data.add(ProfileButtonModel(text = "Edit Profile", icon = R.drawable.ic_edit_profile))
        data.add(ProfileButtonModel(text = "Upload Documents", icon = R.drawable.ic_upload_document_icon))
        data.add(ProfileButtonModel(text = "Verification History", icon = R.drawable.ic_verification_history_icon))
        data.add(ProfileButtonModel(text = "Download Resume", icon = R.drawable.ic_download_resume_icon))
        data.add(ProfileButtonModel(text = "Change Password", icon = R.drawable.ic_change_password_icon))
        data.add(ProfileButtonModel(text = "About", icon = R.drawable.ic_about_icon))
        data.add(ProfileButtonModel(text = "Logout",icon= R.drawable.ic_logout_icon))
        profileListener?.loadRecyclerView(data)
    }
}