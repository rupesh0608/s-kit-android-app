package com.rdktechnologies.skit.ui.loginscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.helperclasses.apiclasses.SignupResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.request.GoogleLoginRequest
import com.rdktechnologies.skit.ui.forgotpasswordscreen.ForgotPasswordScreen
import com.rdktechnologies.skit.ui.homescreen.HomeScreen
import com.rdktechnologies.skit.ui.signupscreen.SignUpScreen
import com.rdktechnologies.skit.utils.AppUtils
import com.technicalrupu.sportsapp.HelperClasses.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginScreen : AppCompatActivity() {
    lateinit var btnLogin: Button
    lateinit var btnGoogleLogin: Button
    lateinit var txtSignUp: TextView
    lateinit var txtForgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        init()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        btnLogin.setOnClickListener {
            startActivity(Intent(this, HomeScreen::class.java))
        }
        txtSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpScreen::class.java))
        }
        btnGoogleLogin.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 101)
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
                googleLogin(
                    firstName = firstName,
                    lastName = "Deshmukh",
                    email = email,
                    picUrl = picUrl.toString()
                )
            }
        } catch (e: Exception) {

            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }




    private fun googleLogin(
        firstName: String?,
        lastName: String?,
        email: String?,
        picUrl: String?
    ) {
        val retrofit = Retrofit()

        retrofit.createWithAuthInterface().googleLogin(
            GoogleLoginRequest(
                firstName = firstName.toString(),
                lastName= lastName.toString(),
                email = email.toString(),
                pic_url = picUrl.toString()
            )
        ).enqueue(object : Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {

                if (response.isSuccessful && response.body() != null) {
                    val signupResponse = response.body() as SignupResponse
                    if (signupResponse.error) {
                        AppUtils().showToast(signupResponse.message, this@LoginScreen)
                    } else {
                        AppUtils().showToast(signupResponse.message, this@LoginScreen)
                        startActivity(Intent(this@LoginScreen, HomeScreen::class.java))
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                val message = t.message.toString()
                AppUtils().showToast(message, this@LoginScreen)
            }
        })


    }


}