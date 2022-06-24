package com.rdktechnologies.skit.helperclasses.apiclasses

import java.security.Provider

data class ServiceResponse(
    var error:Boolean?= false,
    var message:String?=null,
var data:List<ServiceModule>?= mutableListOf()
)
data class ServiceModule(
    var id:Long,
    var image:String,
var name:String,
var isActive:Boolean?=false,
var services: List<Services>?= mutableListOf()
)

data class Services(
    var id:Long,
    var image: String,
    var name: String,
    var link:String,
    var isActive: Boolean?=false
)
