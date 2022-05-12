package com.rdktechnologies.skit.ui.permissioninfoscreen

import android.view.View
import androidx.lifecycle.ViewModel
class PermissionInfoViewModel:ViewModel() {

    var permissionInfoListener:PermissionInfoListener?=null
    fun onGrantPermissionClick(view: View){
        permissionInfoListener?.openSettings()
    }
}