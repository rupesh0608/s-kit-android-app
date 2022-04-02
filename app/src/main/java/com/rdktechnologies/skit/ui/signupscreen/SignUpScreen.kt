package com.rdktechnologies.skit.ui.signupscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.helperclasses.apiclasses.SignupResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.request.GoogleLoginRequest
import com.rdktechnologies.skit.helperclasses.apiclasses.request.SignupRequest
import com.rdktechnologies.skit.ui.homescreen.HomeScreen
import com.rdktechnologies.skit.ui.loginscreen.LoginScreen
import com.rdktechnologies.skit.utils.AppUtils
import com.technicalrupu.sportsapp.HelperClasses.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpScreen : AppCompatActivity() {

    private lateinit var btnSignUp: Button
    lateinit var imgGoogle: ImageView
    lateinit var imgFacebook: ImageView
    lateinit var txtSignIn: TextView
    lateinit var etFirstName: EditText
    lateinit var etLastName: EditText
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var progress: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_screen)
        init()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        btnSignUp.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()
            when {
                firstName.isEmpty() -> {
                    AppUtils().showToast("First name is required!", this@SignUpScreen)
                }
                lastName.isEmpty() -> {
                    AppUtils().showToast("Last name is required!", this@SignUpScreen)
                }
                email.isEmpty() -> {
                    AppUtils().showToast("Email is required!", this@SignUpScreen)
                }
                password.isEmpty() -> {
                    AppUtils().showToast("Password is required!", this@SignUpScreen)
                }
                confirmPassword.isEmpty() -> {
                    AppUtils().showToast("Confirm Password is required!", this@SignUpScreen)
                }
                else -> {
                    simpleSignUp(
                        firstName,
                        lastName,
                        email,
                        password,
                        confirmPassword
                    )
                }
            }


        }
        imgGoogle.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 101)
        }
        imgFacebook.setOnClickListener {
            facebookLogin()
        }

        txtSignIn.setOnClickListener {
            startActivity(Intent(this, LoginScreen::class.java))
        }

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

    private fun init() {
        btnSignUp = findViewById(R.id.btnSignup)
        imgFacebook = findViewById(R.id.ImgFacebook)
        imgGoogle = findViewById(R.id.ImgGoogle)
        txtSignIn = findViewById(R.id.txtSignIn)
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        progress = findViewById(R.id.progress)
//        btnLoginText=findViewById(R.id.btnSinUpText)
    }

    private fun facebookLogin() {
        startActivity(Intent(this, HomeScreen::class.java))
        finish()
    }


    private fun googleLogin(
        firstName: String?,
        lastName: String?,
        email: String?,
        picUrl: String?
    ) {
        imgGoogle.isEnabled = false
        progress.visibility = View.VISIBLE
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
                        imgGoogle.isEnabled = true
                        progress.visibility = View.GONE
                        AppUtils().showToast(signupResponse.message, this@SignUpScreen)
                    } else {
                        imgGoogle.isEnabled = true
                        progress.visibility = View.GONE
                        AppUtils().showToast(signupResponse.message, this@SignUpScreen)
                        startActivity(Intent(this@SignUpScreen, HomeScreen::class.java))
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                progress.visibility = View.GONE
                imgGoogle.isEnabled = true
                val message = t.message.toString()
                AppUtils().showToast(message, this@SignUpScreen)
            }
        })


    }

    private fun simpleSignUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        btnSignUp.isEnabled = false
        progress.visibility = View.VISIBLE
        val retrofit = Retrofit()
        val signupRequest = SignupRequest(firstName, lastName, email, password, confirmPassword)
        retrofit.createWithAuthInterface().signup(
            signupRequest
        ).enqueue(object : Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {

                if (response.isSuccessful && response.body() != null) {
                    val signupResponse = response.body() as SignupResponse
                    if (signupResponse.error) {
                        btnSignUp.isEnabled = true
                        progress.visibility = View.GONE
                        AppUtils().showToast(signupResponse.message, this@SignUpScreen)
                    } else {
                        btnSignUp.isEnabled = true
                        progress.visibility = View.GONE
                        AppUtils().showToast(signupResponse.message, this@SignUpScreen)
                        startActivity(Intent(this@SignUpScreen, LoginScreen::class.java))
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                progress.visibility = View.GONE
                btnSignUp.isEnabled = true
                val message = t.message.toString()
                AppUtils().showToast(message, this@SignUpScreen)
            }
        })


    }

}