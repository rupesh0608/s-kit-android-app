package com.rdktechnologies.skit.ui.permissioninfoscreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.databinding.ActivityPermissionInfoScreenBinding


class PermissionInfoScreen : AppCompatActivity(),PermissionInfoListener {
    lateinit var binding: ActivityPermissionInfoScreenBinding
    lateinit var viewModel: PermissionInfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_permission_info_screen) as ActivityPermissionInfoScreenBinding
        viewModel= ViewModelProviders.of(this).get(PermissionInfoViewModel::class.java)
        binding.permissionInfoViewModel=viewModel
        viewModel.permissionInfoListener=this
    }

    override fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}