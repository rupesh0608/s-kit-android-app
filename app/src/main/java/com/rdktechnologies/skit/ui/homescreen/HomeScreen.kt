package com.rdktechnologies.skit.ui.homescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.ui.homescreen.fragments.bookmarkfragment.BookmarksFragment
import com.rdktechnologies.skit.ui.homescreen.fragments.govtexamsfragment.GovtExamsFragment
import com.rdktechnologies.skit.ui.homescreen.fragments.homefragment.HomeFragment
import com.rdktechnologies.skit.ui.homescreen.fragments.servicefragment.ServicesFragment
import com.rdktechnologies.skit.ui.profilescreen.ProfileScreen
import com.rdktechnologies.skit.utils.AppUtils

class HomeScreen : AppCompatActivity() {
    lateinit var ivHome:ImageView
    lateinit var ivGovtExams:ImageView
    lateinit var ivServices:ImageView
    lateinit var ivBookmarks:ImageView
    lateinit var ivProfile:ImageView
    lateinit var btnHome:Button
    lateinit var btnGovtExams:Button
    lateinit var btnServices:Button
    lateinit var btnBookmarks:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
         init()
        setBottomNavigation()
        AppUtils().changeFragment(R.id.fragmentContainer, HomeFragment(),this@HomeScreen)

        ivHome.setOnClickListener{
            setBottomNavigationWithFragment(btnHome,ivHome, HomeFragment())
        }
        ivGovtExams.setOnClickListener{
            setBottomNavigationWithFragment(btnGovtExams,ivGovtExams, GovtExamsFragment())
        }
        ivServices.setOnClickListener{
            setBottomNavigationWithFragment(btnServices,ivServices, ServicesFragment())
        }
        ivBookmarks.setOnClickListener{
            setBottomNavigationWithFragment(btnBookmarks,ivBookmarks, BookmarksFragment())
        }
        ivProfile.setOnClickListener{
            startActivity(Intent(this@HomeScreen,ProfileScreen::class.java))
        }

    }

   private fun setBottomNavigationWithFragment(btnId:Button, ivId: ImageView,fragment: Fragment){
        val ivArray= arrayOf(ivHome,ivGovtExams,ivServices,ivBookmarks)

       val btnArray= arrayOf(btnHome,btnGovtExams,btnServices,btnBookmarks)

       for( iv in ivArray){
           if(iv!=ivId){
               iv.visibility=View.VISIBLE
           }else{
               iv.visibility=View.GONE
           }
       }
       for( btn in btnArray){
           if(btn!=btnId){
               btn.visibility=View.GONE
           }else{
               btn.visibility=View.VISIBLE
           }
       }

       AppUtils().changeFragment(R.id.fragmentContainer,fragment,this@HomeScreen)

    }

   private fun setBottomNavigation(){
        ivHome.visibility= View.GONE
        ivServices.visibility= View.VISIBLE
        ivGovtExams.visibility= View.VISIBLE
        ivBookmarks.visibility= View.VISIBLE
        ivProfile.visibility= View.VISIBLE
        btnHome.visibility=View.VISIBLE
        btnGovtExams.visibility=View.GONE
        btnServices.visibility=View.GONE
        btnBookmarks.visibility=View.GONE
    }
 private   fun init(){
        ivHome=findViewById(R.id.ivHome)
        ivGovtExams=findViewById(R.id.ivGovtExams)
        ivBookmarks=findViewById(R.id.ivBookmark)
        ivServices=findViewById(R.id.ivServices)
        ivProfile=findViewById(R.id.ivProfile)
        btnHome=findViewById(R.id.btnHome)
        btnGovtExams=findViewById(R.id.btnGovtExams)
        btnServices=findViewById(R.id.btnServices)
        btnBookmarks=findViewById(R.id.btnBookmarks)
    }
}