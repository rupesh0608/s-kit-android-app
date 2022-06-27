package com.rdktechnologies.skit.ui.loginscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.databinding.ActivityLoginScreenBinding
import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse
import com.rdktechnologies.skit.ui.forgotpasswordscreen.ForgotPasswordScreen
import com.rdktechnologies.skit.ui.homescreen.HomeScreen
import com.rdktechnologies.skit.ui.profilescreen.ProfileScreen
import com.rdktechnologies.skit.ui.profilescreen.subactivity.uploaddocument.UploadDocumentsScreen
import com.rdktechnologies.skit.ui.signupscreen.SignUpScreen
import com.rdktechnologies.skit.utils.*

class LoginScreen : AppCompatActivity(), LoginListener {
    lateinit var binding: ActivityLoginScreenBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login_screen
        ) as ActivityLoginScreenBinding
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel
        viewModel.listener = this
        viewModel.context=this
        binding.progressView.gone()
        binding.progressLayout.gone()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val acct = GoogleSignIn.getLastSignedInAccount(this)
            if (acct != null) {
                viewModel.email= acct.email?.toString()
                viewModel.picUrl= acct.photoUrl?.toString()
                if (acct.displayName!!.lastIndexOf(' ')== -1) {
                    viewModel.firstName=acct.displayName!!
                    viewModel.lastName=""
                }else{
                    viewModel.firstName = acct.displayName!!.substring(0,acct.displayName!!.lastIndexOf(' '))
                    viewModel.lastName= acct.displayName!!.substring(acct.displayName!!.lastIndexOf(' ') + 1)
                }
                viewModel.googleLoginApiCall()
            }
        } catch (e: Exception) {
            shortToast(e.message!!)
        }
    }

    override fun onError(message: String) {
//        binding.progressView.gone()
//        binding.progressLayout.gone()
        hideProgressAlert()
        shortToast(message)
    }

    override fun onStarted() {
        mGoogleSignInClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        )
    }


    override fun onSuccess(response:LoginResponse) {
        SharedPreference(this@LoginScreen).setLoginResponse(response as LoginResponse)
//            binding.progressView.gone()
//            binding.progressLayout.gone()
        hideProgressAlert()
            shortToast(response.message!!)
        if(response.data?.verification!=null){
            if(response.data!!.verification!!.count==0 && response.data!!.verification!!.status=="none"){
                startActivity(Intent(this@LoginScreen, UploadDocumentsScreen::class.java))
                finish()
                return
            }
            if(response.data!!.verification!!.count!! > 0 && response.data!!.verification!!.status=="pending"){
                startActivity(Intent(this@LoginScreen,ProfileScreen::class.java))
                finish()
                return
            }
                SharedPreference(this@LoginScreen).setLoginResponse(response)
                startActivity(Intent(this@LoginScreen, HomeScreen::class.java))
                finish()
                return
        }
    }

    override fun goToSignUpScreen() {
        startActivity(Intent(this@LoginScreen, SignUpScreen::class.java))
        finish()
    }

    override fun googleLogin() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 101)
    }

    override fun goToForgotPasswordScreen() {
        startActivity(Intent(this, ForgotPasswordScreen::class.java))
    }

    override fun showProgress() {
//        binding.progressLayout.show()
//        binding.progressView.show()
        showProgressAlert()
    }


}