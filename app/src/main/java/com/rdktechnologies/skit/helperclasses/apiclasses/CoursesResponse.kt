package com.rdktechnologies.skit.helperclasses.apiclasses

data class CoursesResponse(
    var count: Long,
    var next: String,
    var previous: String,
    var results: List<Course>
)

data class Course(
    var id: Long,
    var title: String,
    var url: String,
    var image_125_H: String,
    var image_240x135: String,
    var image_480x270: String,
    var headline: String
)
