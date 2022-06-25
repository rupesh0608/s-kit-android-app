package com.rdktechnologies.skit.helperclasses.apiclasses

data class EligibleJobResponse(
   var error:Boolean?=true,
    var message:String?="",
var data: List<Jobs>?= mutableListOf<Jobs>()
)
data class Jobs(
    var id:Long,
    var postName:String,
    var boardName: String,
    var qualifications:String,
    var link:String,
    var postDate:String,
    var lastDate:String,
    var category:String,
    var status:String,
    var remainingDays:Int
)

