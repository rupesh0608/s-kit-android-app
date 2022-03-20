package com.rdktechnologies.skit.ui.homescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.login_page

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, login_page::class.java)
            startActivity(intent)
            finish()

    }, 3000)
}
    private fun setTransparentStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}