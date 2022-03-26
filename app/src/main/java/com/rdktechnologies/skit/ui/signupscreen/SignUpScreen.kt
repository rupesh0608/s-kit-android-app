package com.rdktechnologies.skit.ui.signupscreen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.ui.homescreen.HomeScreen
import com.rdktechnologies.skit.ui.loginscreen.LoginScreen

class SignUpScreen : AppCompatActivity() {

    lateinit var btnSignUp: Button
    lateinit var imgGoogle: ImageView
    lateinit var imgFacebook: ImageView
    lateinit var txtSignIn: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_screen)
        init()

        btnSignUp.setOnClickListener {
            simpleLogin()
        }
        imgGoogle.setOnClickListener {
            googleLogin()
        }
        imgFacebook.setOnClickListener {
            facebookLogin()
        }

        txtSignIn.setOnClickListener{
            startActivity(Intent(this,LoginScreen::class.java))
        }

    }

    private fun init() {
        btnSignUp = findViewById(R.id.btnSignup)
        imgFacebook = findViewById(R.id.ImgFacebook)
        imgGoogle = findViewById(R.id.ImgGoogle)
        txtSignIn = findViewById(R.id.txtSignIn)
    }

    private fun facebookLogin() {
        startActivity(Intent(this, HomeScreen::class.java))
        finish()
    }

    private fun googleLogin() {
        startActivity(Intent(this,HomeScreen::class.java))
        finish()
    }

    private fun simpleLogin() {
        startActivity(Intent(this,HomeScreen::class.java))
        finish()
    }

}