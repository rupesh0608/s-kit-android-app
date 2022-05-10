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
import com.rdktechnologies.skit.ui.signupscreen.SignUpScreen
import com.rdktechnologies.skit.utils.SharedPreference
import com.rdktechnologies.skit.utils.shortToast

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
                val firstName = acct.displayName
                val email = acct.email?.toString()
                val picUrl = acct.photoUrl?.toString()
                viewModel.doGoogleLogin(
                    firstName = firstName,
                    lastName = "Deshmukh",
                    email = email,
                    picUrl = picUrl.toString()
                )
            }
        } catch (e: Exception) {
            shortToast(e.message!!)
        }
    }

    override fun onError(message: String) {
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

    override fun onSuccess(response: LoginResponse) {
        SharedPreference(this@LoginScreen).setLoginResponse(response)
        shortToast(response.message!!)
        startActivity(Intent(this@LoginScreen, HomeScreen::class.java))
        finish()
    }

    override fun goToSignUpScreen() {
        startActivity(Intent(this, SignUpScreen::class.java))
        finish()
    }

    override fun googleLogin() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 101)
    }

    override fun goToForgotPasswordScreen() {
        startActivity(Intent(this, ForgotPasswordScreen::class.java))
    }


}