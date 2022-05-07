package com.rdktechnologies.skit.ui.loginscreen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.ui.forgotpasswordscreen.ForgotPasswordScreen
import com.rdktechnologies.skit.ui.homescreen.HomeScreen
import com.rdktechnologies.skit.ui.signupscreen.SignUpScreen

class LoginScreen : AppCompatActivity() {
    lateinit var btnLogin: Button
    lateinit var btnGoogleLogin: Button
    lateinit var txtSignUp: TextView
    lateinit var txtForgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        init()
        btnLogin.setOnClickListener {
            startActivity(Intent(this, HomeScreen::class.java))
        }
        txtSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpScreen::class.java))
        }
        btnGoogleLogin.setOnClickListener {
            googleLogin()
        }
        txtForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordScreen::class.java))
        }
    }

    private fun init() {
        btnLogin = findViewById(R.id.btnLogin)
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin)
        txtSignUp = findViewById(R.id.txtSignUp)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
    }

    private fun googleLogin() {
      startActivity(Intent(this,HomeScreen::class.java))
        finish()
    }

}