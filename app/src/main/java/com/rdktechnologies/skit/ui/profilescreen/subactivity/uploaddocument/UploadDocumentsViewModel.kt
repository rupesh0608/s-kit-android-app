package com.rdktechnologies.skit.ui.profilescreen.subactivity.uploaddocument

import android.view.View
import androidx.lifecycle.ViewModel
import com.rdktechnologies.skit.utils.Constants

class UploadDocumentsViewModel : ViewModel() {

    var listener: UploadDocumentsListener? = null
    fun onDocument10CameraClicked(view: View) {
        listener?.openCamera(Constants.UPLOAD_DOCUMENT_10th_CAMERA)
    }

    fun onDocument10GalleryClicked(view: View) {
        listener?.openGallery(Constants.UPLOAD_DOCUMENT_10th_GALLERY)
    }

    fun onDocument12CameraClicked(view: View) {
        listener?.openCamera(Constants.UPLOAD_DOCUMENT_12th_CAMERA)
    }

    fun onDocument12GalleryClicked(view: View) {
        listener?.openGallery(Constants.UPLOAD_DOCUMENT_12th_GALLERY)
    }

    fun onDocumentGraduationCameraClicked(view: View) {
        listener?.openCamera(Constants.UPLOAD_DOCUMENT_GRADUATION_CAMERA)
    }

    fun onDocumentGraduationGalleryClicked(view: View) {
        listener?.openGallery(Constants.UPLOAD_DOCUMENT_GRADUATION_GALLERY)
    }

    fun onDocumentCertificateCameraClicked(view: View) {
        listener?.openCamera(Constants.UPLOAD_DOCUMENT_CERTIFICATE_CAMERA)
    }

    fun onDocumentCertificateGalleryClicked(view: View) {
        listener?.openGallery(Constants.UPLOAD_DOCUMENT_CERTIFICATE_GALLERY)
    }
}