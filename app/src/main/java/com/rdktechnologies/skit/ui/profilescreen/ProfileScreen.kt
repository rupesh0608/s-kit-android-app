package com.rdktechnologies.skit.ui.profilescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdktechnologies.skit.R

class ProfileScreen : AppCompatActivity() {

    lateinit var recyclerview:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_screen)
        init()
        val data = ArrayList<ProfileButtonModel>()
        data.add(ProfileButtonModel(text = "Edit Profile", icon = R.drawable.ic_edit_profile))
        data.add(ProfileButtonModel(text = "Upload Documents", icon = R.drawable.ic_upload_document_icon))
        data.add(ProfileButtonModel(text = "Verification History", icon = R.drawable.ic_verification_history_icon))
        data.add(ProfileButtonModel(text = "Download Resume", icon = R.drawable.ic_download_resume_icon))
        data.add(ProfileButtonModel(text = "Change Password", icon = R.drawable.ic_change_password_icon))
        data.add(ProfileButtonModel(text = "About", icon = R.drawable.ic_about_icon))
        data.add(ProfileButtonModel(text = "Logout",icon=R.drawable.ic_logout_icon))
        val adapter = ProfileButtonAdapter(this,data)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

    }

    private fun init(){
        recyclerview=findViewById(R.id.settingRecyclerView)
    }
}