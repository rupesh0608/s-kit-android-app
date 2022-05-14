package com.rdktechnologies.skit.helperclasses.data.entities

data class User (
    var id:Long?=null,
    var firstName:String?=null,
    var lastName:String?=null,
    var email:String?=null,
    var picUrl:String?= null,
    var phoneNumber:Long?=null,
    var isGoogleLogin:Boolean?=false
        )
