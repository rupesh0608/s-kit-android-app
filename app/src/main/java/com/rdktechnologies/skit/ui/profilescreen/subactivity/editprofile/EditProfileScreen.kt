package com.rdktechnologies.skit.ui.profilescreen.subactivity.editprofile

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.databinding.ActivityEditProfileScreenBinding
import com.rdktechnologies.skit.databinding.ActivityProfileScreenBinding
import com.rdktechnologies.skit.ui.profilescreen.ProfileViewModel
import com.rdktechnologies.skit.utils.SharedPreference

class EditProfileScreen : AppCompatActivity(),EditProfileListener {
    lateinit var binding: ActivityEditProfileScreenBinding
    lateinit var viewModel: EditProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=
            DataBindingUtil.setContentView(this,R.layout.activity_edit_profile_screen) as ActivityEditProfileScreenBinding
        viewModel= ViewModelProviders.of(this).get(EditProfileViewModel::class.java)
        binding.editProfileViewModel=viewModel
        viewModel.editProfileListener = this
        viewModel.onStarted()
    }

    @SuppressLint("SetTextI18n")
    override fun onStarted() {
        if(SharedPreference(this).getProfile()?.picUrl==null){
            Glide.with(this).load("https://source.unsplash.com/user/c_v_r/100x100").into(binding.imgProfile);
        }else{
            Glide.with(this).load(SharedPreference(this).getProfile()?.picUrl).into(binding.imgProfile);
        }
        val profile=SharedPreference(this).getProfile()
        viewModel.firstName=profile?.firstName
        viewModel.lastName=profile?.lastName
        viewModel.email=profile?.email
    }

    override fun onFailure() {
        TODO("Not yet implemented")
    }

    override fun onSuccess() {
        TODO("Not yet implemented")
    }
}