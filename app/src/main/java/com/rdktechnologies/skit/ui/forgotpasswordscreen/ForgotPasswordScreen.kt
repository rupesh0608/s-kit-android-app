package com.rdktechnologies.skit.ui.forgotpasswordscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.ui.loginscreen.LoginScreen

class ForgotPasswordScreen : AppCompatActivity() {
    lateinit var btnSend:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_screen)
        init()
        btnSend.setOnClickListener {
            startActivity(Intent(this,LoginScreen::class.java))
            finish()
        }
    }
    private fun init(){
        btnSend=findViewById(R.id.btnSend)
    }

}