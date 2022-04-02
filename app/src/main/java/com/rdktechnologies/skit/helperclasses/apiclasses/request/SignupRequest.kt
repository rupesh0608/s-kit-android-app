package com.rdktechnologies.skit.helperclasses.apiclasses.request

import retrofit2.http.Field

data class SignupRequest (
    var first_name: String,
   var last_name: String,
    var email: String,
   var password: String,
    var confirm_password: String
        )