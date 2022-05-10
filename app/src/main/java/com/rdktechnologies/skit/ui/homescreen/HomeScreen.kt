package com.rdktechnologies.skit.ui.homescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.databinding.ActivityHomeScreenBinding
import com.rdktechnologies.skit.databinding.ActivitySignupScreenBinding
import com.rdktechnologies.skit.ui.homescreen.fragments.bookmarkfragment.BookmarksFragment
import com.rdktechnologies.skit.ui.homescreen.fragments.govtexamsfragment.GovtExamsFragment
import com.rdktechnologies.skit.ui.homescreen.fragments.homefragment.HomeFragment
import com.rdktechnologies.skit.ui.homescreen.fragments.servicefragment.ServicesFragment
import com.rdktechnologies.skit.ui.profilescreen.ProfileScreen
import com.rdktechnologies.skit.ui.signupscreen.SignUpViewModel
import com.rdktechnologies.skit.utils.AppUtils
import com.rdktechnologies.skit.utils.Constants
import com.rdktechnologies.skit.utils.SharedPreference
import com.rdktechnologies.skit.utils.shortToast

class HomeScreen : AppCompatActivity(),HomeListener {


    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var  viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_home_screen) as ActivityHomeScreenBinding
        viewModel= ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding.homeViewModel=viewModel
        viewModel.listener=this
        viewModel.onStarted()
    }


    override fun onStarted() {
        binding.ivHome.visibility= View.GONE
        binding.ivServices.visibility= View.VISIBLE
        binding.ivGovtExams.visibility= View.VISIBLE
        binding.ivBookmark.visibility= View.VISIBLE
        binding.ivProfile.visibility= View.VISIBLE
        binding.btnHome.visibility=View.VISIBLE
        binding.btnGovtExams.visibility=View.GONE
        binding.btnServices.visibility=View.GONE
        binding.btnBookmarks.visibility=View.GONE
    }

    override fun changeFragment(fragment: Fragment,id:String) {
        val ivArray= arrayOf(binding.ivHome,binding.ivGovtExams,binding.ivServices,binding.ivBookmark)

        val btnArray= arrayOf(binding.btnHome,binding.btnGovtExams,binding.btnServices,binding.btnBookmarks)
        lateinit var btnId:Button
        lateinit var ivId:ImageView
        when(id){
            Constants.FRAGMENT_HOME-> {
                btnId=binding.btnHome
                ivId=binding.ivHome
            }

            Constants.FRAGMENT_GOVT_EXAMS->
            {
                btnId=binding.btnGovtExams
                ivId=binding.ivGovtExams
            }

            Constants.FRAGMENT_SERVICES ->{
                btnId=binding.btnServices
                ivId=binding.ivServices
            }

            Constants.FRAGMENT_BOOKMARKS->
            {
                btnId=binding.btnBookmarks
                ivId=binding.ivBookmark
            }

            else ->{
                btnId=binding.btnHome
                ivId=binding.ivHome
            }
        }
        for (iv in ivArray) {
            if (iv != ivId) {
                iv.visibility = View.VISIBLE
            } else {
                iv.visibility = View.GONE
            }
        }
        for (btn in btnArray) {
            if (btn !=btnId) {
                btn.visibility = View.GONE
            } else {
                btn.visibility = View.VISIBLE
            }
        }
        AppUtils().changeFragment(R.id.fragmentContainer,fragment,this)
    }

    override fun goToProfileScreen() {
        startActivityForResult(Intent(this,ProfileScreen::class.java),Constants.ACTIVITY_RESULT_LOGOUT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Constants.ACTIVITY_RESULT_LOGOUT && requestCode==Constants.ACTIVITY_RESULT_LOGOUT){
            shortToast("Successfully Logged out.")
            SharedPreference(this).clearLoginResponse()
            finish()
        }
    }
}