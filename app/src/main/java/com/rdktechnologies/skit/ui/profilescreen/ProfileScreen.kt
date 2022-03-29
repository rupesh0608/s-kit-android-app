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
        data.add(ProfileButtonModel(text = "Edit Profile"))
        data.add(ProfileButtonModel(text = "Upload Documents"))
        data.add(ProfileButtonModel(text = "Verification History"))
        data.add(ProfileButtonModel(text = "Download Resume"))
        data.add(ProfileButtonModel(text = "Change Password"))
        data.add(ProfileButtonModel(text = "About"))
        data.add(ProfileButtonModel(text = "Logout"))
        val adapter = ProfileButtonAdapter(this,data)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

    }

    private fun init(){
        recyclerview=findViewById(R.id.settingRecyclerView)
    }
}