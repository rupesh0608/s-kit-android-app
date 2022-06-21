package com.rdktechnologies.skit.ui.profilescreen.subactivity.uploaddocument

interface UploadDocumentsListener {
    fun openGallery(requestCode:Int)
    fun onStarted()
    fun openCamera(requestCode: Int)
}