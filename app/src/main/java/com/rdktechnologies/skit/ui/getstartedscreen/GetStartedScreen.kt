package com.rdktechnologies.skit.ui.getstartedscreen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.ui.signupscreen.SignUpScreen
import com.rdktechnologies.skit.utils.SharedPreference

class GetStartedScreen : AppCompatActivity() {
    lateinit var btnGetStarted: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)
        init()

        btnGetStarted.setOnClickListener {
            SharedPreference(this@GetStartedScreen).setGetStartedPageVisited(true)
            startActivity(Intent(this@GetStartedScreen, SignUpScreen::class.java))
            finish()
        }
    }

    private fun init() {
        btnGetStarted = findViewById(R.id.btnGetStarted)
    }
}