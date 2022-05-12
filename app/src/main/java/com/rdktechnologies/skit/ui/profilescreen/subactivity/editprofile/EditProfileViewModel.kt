package com.rdktechnologies.skit.ui.profilescreen.subactivity.editprofile

import androidx.lifecycle.ViewModel

class EditProfileViewModel:ViewModel(){

    var firstName:String?=""
    var lastName:String?=""
    var email:String?=""
    var phoneNumber:String?=""
     var editProfileListener:EditProfileListener?=null

    fun onStarted(){
        editProfileListener?.onStarted()
    }
}