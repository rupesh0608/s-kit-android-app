package com.rdktechnologies.skit.helperclasses.apiclasses

data class SignupResponse(
    var error:Boolean=true,
    var statusCode:Long,
    var message:String,
)
