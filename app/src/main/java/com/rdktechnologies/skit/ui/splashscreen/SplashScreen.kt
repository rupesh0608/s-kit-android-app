package com.rdktechnologies.skit.ui.splashscreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.databinding.ActivitySplashScreenBinding
import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse
import com.rdktechnologies.skit.ui.getstartedscreen.GetStartedScreen
import com.rdktechnologies.skit.ui.loginscreen.LoginScreen
import com.rdktechnologies.skit.ui.permissioninfoscreen.PermissionInfoScreen
import com.rdktechnologies.skit.ui.profilescreen.subactivity.uploaddocument.UploadDocumentsScreen
import com.rdktechnologies.skit.utils.AppPermissions
import com.rdktechnologies.skit.utils.SharedPreference
import com.rdktechnologies.skit.utils.longToast
import com.rdktechnologies.skit.utils.shortToast


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity(), SplashListener {
    lateinit var binding: ActivitySplashScreenBinding
    lateinit var viewModel: SplashViewModel
    var PERMISSION_ALL = 1
    var PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_splash_screen
        ) as ActivitySplashScreenBinding
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        binding.splashViewModel = viewModel
        viewModel.splashListener = this
        viewModel.getRequiredPermissions()
        viewModel.startHandler()
    }

    override fun onStarted() {
        binding.ivLogo.startAnimation(
            AnimationUtils.loadAnimation(
                this, R.anim.zoom_in
            )
        )
        binding.ivIllustrations.startAnimation(
            AnimationUtils.loadAnimation(
                this, R.anim.slide_up
            )
        )

    }

    override fun onSuccess(loginResponse: LoginResponse?) {
        val intent = Intent(this, UploadDocumentsScreen::class.java)
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
        if (SharedPreference(this@SplashScreen).isGetStartedPageVisited()) {
            if (SharedPreference(this@SplashScreen).getLoginResponse() != null) {
                onSuccess(SharedPreference(this@SplashScreen).getLoginResponse())
            } else {
                onFailure()
            }
        } else {
            onFirstTimeVisit()
        }
    }

    override fun checkPermissions() {
        if (!AppPermissions(this).hasPermissions(*PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startHandler()
            }, 3000)

        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ((grantResults.isNotEmpty()) &&
            (grantResults[0]
                    + grantResults[1]
                    + grantResults[2]
                    == PackageManager.PERMISSION_GRANTED
                    )
        ) {
            Handler(Looper.getMainLooper()).postDelayed({
                startHandler()
            }, 3000)
        } else {
            val intent = Intent(this, PermissionInfoScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}