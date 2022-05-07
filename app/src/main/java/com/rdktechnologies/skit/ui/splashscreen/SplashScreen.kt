package com.rdktechnologies.skit.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.ui.getstartedscreen.GetStartedScreen
import com.rdktechnologies.skit.ui.homescreen.HomeScreen
import com.rdktechnologies.skit.ui.loginscreen.LoginScreen
import com.rdktechnologies.skit.utils.SharedPreference


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setAnimations()
        Handler(Looper.getMainLooper()).postDelayed({
            if(SharedPreference(this@SplashScreen).isGetStartedPageVisited()){
                if(SharedPreference(this@SplashScreen).getLoginResponse()!=null){
                    val intent = Intent(this, HomeScreen::class.java)
                    startActivity(intent)
                    finish()
                }else {
                    val intent = Intent(this, LoginScreen::class.java)
                    startActivity(intent)
                    finish()
                }
            }else {
                val intent = Intent(this, GetStartedScreen::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }
   private fun setAnimations(){
      val animLogo = AnimationUtils.loadAnimation(
          applicationContext,R.anim.zoom_in);
       findViewById<ImageView>(R.id.ivLogo).startAnimation(animLogo)

       val animIllustration = AnimationUtils.loadAnimation(
           applicationContext,R.anim.slide_up);
       findViewById<ImageView>(R.id.ivIllustrations).startAnimation(animIllustration)
   }

}