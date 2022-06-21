package com.rdktechnologies.skit.helperclasses.apiclasses

data class LoginResponse(
    var error:Boolean?= false,
    var statusCode:Int?=null,
var message:String?=null,
var token:String?=null,
var data:ProfileData?=null ,
)
data class ProfileData(
     var id:Long?=null,
    var firstName:String?=null,
    var lastName:String?=null,
    var email:String?=null,
    var picUrl:String?= null,
     var phoneNumber:Long?=null,
    var isGoogleLogin:Boolean?=false,
var verification:Verification?=null
)
data class Verification(
    var count:Int?=0,
    var status:String?="none"
)