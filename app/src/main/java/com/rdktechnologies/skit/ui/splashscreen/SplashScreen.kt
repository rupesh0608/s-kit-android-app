package com.rdktechnologies.skit.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.databinding.ActivitySplashScreenBinding
import com.rdktechnologies.skit.ui.getstartedscreen.GetStartedScreen
import com.rdktechnologies.skit.ui.homescreen.HomeScreen
import com.rdktechnologies.skit.ui.loginscreen.LoginScreen
import com.rdktechnologies.skit.utils.SharedPreference


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity(), SplashListener {
    lateinit var binding: ActivitySplashScreenBinding
    lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_splash_screen
        ) as ActivitySplashScreenBinding
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        binding.splashViewModel=viewModel
        viewModel.splashListener = this
        viewModel.getRequiredPermissions()
        viewModel.startHandler()
    }

    override fun onStarted() {
        binding.ivLogo.startAnimation(AnimationUtils.loadAnimation(
            this, R.anim.zoom_in
        ))
        binding.ivIllustrations.startAnimation(AnimationUtils.loadAnimation(
            this, R.anim.slide_up
        ))
    }

    override fun onSuccess() {
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFailure() {
        val intent = Intent(this, LoginScreen::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFirstTimeVisit() {
        val intent = Intent(this, GetStartedScreen::class.java)
        startActivity(intent)
        finish()
    }

    override fun startHandler() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (SharedPreference(this@SplashScreen).isGetStartedPageVisited()) {
                if (SharedPreference(this@SplashScreen).getLoginResponse() != null) {
                    onSuccess()
                } else {
                    onFailure()
                }
            } else {
                onFirstTimeVisit()
            }
        }, 3000)
    }


}