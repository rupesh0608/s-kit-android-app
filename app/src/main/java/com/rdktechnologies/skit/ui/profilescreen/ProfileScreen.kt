package com.rdktechnologies.skit.ui.profilescreen

import android.annotation.SuppressLint
import android.content.Intent
import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.databinding.ActivityProfileScreenBinding
import com.rdktechnologies.skit.databinding.ActivitySplashScreenBinding
import com.rdktechnologies.skit.ui.splashscreen.SplashViewModel
import com.rdktechnologies.skit.utils.SharedPreference

class ProfileScreen : AppCompatActivity() ,ProfileListener{
    lateinit var binding: ActivityProfileScreenBinding
    lateinit var viewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_profile_screen) as ActivityProfileScreenBinding
        viewModel= ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        binding.profileViewModel=viewModel
        viewModel.profileListener = this
        viewModel.onStarted()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        setDetails()
    }

    @SuppressLint("SetTextI18n")
    override fun setDetails() {
       binding.txtName.text="${SharedPreference(this).getProfile()?.firstName} ${SharedPreference(this).getProfile()?.lastName}"
        if(SharedPreference(this).getProfile()?.picUrl==null){
            Glide.with(this).load("https://source.unsplash.com/user/c_v_r/100x100").into(binding.imgProfile)
        }else{
            Glide.with(this).load(SharedPreference(this).getProfile()?.picUrl).into(binding.imgProfile)
        }
    }

    override fun loadRecyclerView(data:ArrayList<ProfileButtonModel>) {
        val adapter = ProfileButtonAdapter(this,data)
        binding.settingRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.settingRecyclerView.adapter = adapter
    }

}