package com.rdktechnologies.skit.ui.signupscreen

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.databinding.ActivitySignupScreenBinding
import com.rdktechnologies.skit.helperclasses.apiclasses.SignupResponse
import com.rdktechnologies.skit.ui.loginscreen.LoginScreen
import com.rdktechnologies.skit.utils.hideProgressAlert
import com.rdktechnologies.skit.utils.shortToast
import com.rdktechnologies.skit.utils.showProgressAlert

class SignUpScreen : AppCompatActivity(),SignUpListener {
   lateinit var binding: ActivitySignupScreenBinding
   lateinit var viewModel:SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_signup_screen) as ActivitySignupScreenBinding
        viewModel= ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        binding.signUpViewModel=viewModel
        viewModel.listener=this
        viewModel.context=this
    }

    override fun onError(message: String) {
        hideProgressAlert()
        shortToast(message)
    }

    override fun onStarted() {
        showProgressAlert()
    }

    override fun onSuccess(response: SignupResponse) {
       hideProgressAlert()
        shortToast(response.message)
        startActivity(Intent(this, LoginScreen::class.java))
        finish()
    }

    override fun goToLoginScreen() {
        startActivity(Intent(this, LoginScreen::class.java))
        finish()
    }



}