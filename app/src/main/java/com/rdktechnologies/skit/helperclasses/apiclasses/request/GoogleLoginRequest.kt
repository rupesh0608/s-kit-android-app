package com.rdktechnologies.skit.helperclasses.apiclasses.request

import retrofit2.http.Field

data class GoogleLoginRequest(
    var firstName: String,

    var lastName: String,

    var pic_url: String,

    var email: String
)