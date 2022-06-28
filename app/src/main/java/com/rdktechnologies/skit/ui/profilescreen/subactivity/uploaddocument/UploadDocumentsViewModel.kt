package com.rdktechnologies.skit.ui.profilescreen.subactivity.uploaddocument

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse
import com.rdktechnologies.skit.utils.Constants
import com.rdktechnologies.skit.utils.SharedPreference
import com.technicalrupu.sportsapp.HelperClasses.Retrofit
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadDocumentsViewModel : ViewModel() {
    var document10thFile: File? = null
    var document12thFile: File? = null
    var documentGraduationFile: File? = null
    var documentExperienceCertificateFile: File? = null
    var listener: UploadDocumentsListener? = null
    var context: Context? = null
    fun onDocument10CameraClicked(view: View) {
        listener?.openCamera(Constants.UPLOAD_DOCUMENT_10th_CAMERA)
    }

    fun onDocument10GalleryClicked(view: View) {
        listener?.openGallery(Constants.UPLOAD_DOCUMENT_10th_GALLERY)
    }

    fun onDocument12CameraClicked(view: View) {
        listener?.showProgress()
        if(document10thFile==null){
            listener?.onFaliure("Please Upload 10th marksheet...")
        }else {
            listener?.hideProgress()
            listener?.openCamera(Constants.UPLOAD_DOCUMENT_12th_CAMERA)
        }
    }

    fun onDocument12GalleryClicked(view: View) {
        listener?.showProgress()
        if(document10thFile==null){
            listener?.onFaliure("Please Upload 10th marksheet...")
        }else {
            listener?.hideProgress()
            listener?.openGallery(Constants.UPLOAD_DOCUMENT_12th_GALLERY)
        }
    }

    fun onDocumentGraduationCameraClicked(view: View) {
        listener?.showProgress()
        if(document10thFile==null ){
            listener?.onFaliure("Please Upload 10th marksheet...")
        }else if(document12thFile==null){
            listener?.onFaliure("Please Upload 12th marksheet...")

        } else {
            listener?.hideProgress()
            listener?.openCamera(Constants.UPLOAD_DOCUMENT_GRADUATION_CAMERA)
        }
    }

    fun onDocumentGraduationGalleryClicked(view: View) {
        listener?.showProgress()
        if(document10thFile==null ){
            listener?.onFaliure("Please Upload 10th marksheet...")
        }else if(document12thFile==null){
            listener?.onFaliure("Please Upload 12th marksheet...")

        } else {
            listener?.hideProgress()
            listener?.openGallery(Constants.UPLOAD_DOCUMENT_GRADUATION_GALLERY)
        }
    }

    fun onDocumentCertificateCameraClicked(view: View) {
        listener?.openCamera(Constants.UPLOAD_DOCUMENT_CERTIFICATE_CAMERA)
    }

    fun onDocumentCertificateGalleryClicked(view: View) {
        listener?.openGallery(Constants.UPLOAD_DOCUMENT_CERTIFICATE_GALLERY)
    }

    fun onSendForVerificationClicked(view: View) {
        listener?.showProgress()
        if (documentExperienceCertificateFile == null) {
            if (documentGraduationFile != null) {
                Retrofit(context!!).createWithAuthInterface().uploadDocuments(
                    SharedPreference(context!!).getProfile()?.id!!,
                    MultipartBody.Part.createFormData(
                        "document10",
                        document10thFile!!.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), document10thFile!!)
                    ),
                    MultipartBody.Part.createFormData(
                        "document12",
                        document12thFile!!.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), document12thFile!!)
                    ),
                    MultipartBody.Part.createFormData(
                        "documentGraduation",
                        documentGraduationFile!!.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), documentGraduationFile!!)
                    )
                ).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            listener?.onSuccess(response.body()!!.message!!)
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        listener?.onFaliure(t.message!!)
                    }
                })
            } else if (document12thFile != null) {
                Retrofit(context!!).createWithAuthInterface().uploadDocuments(
                    SharedPreference(context!!).getProfile()?.id!!,
                    MultipartBody.Part.createFormData(
                        "document10",
                        document10thFile!!.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), document10thFile!!)
                    ),
                    MultipartBody.Part.createFormData(
                        "document12",
                        document12thFile!!.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), document12thFile!!)
                    )
                ).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            listener?.onSuccess(response.body()!!.message!!)
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        listener?.onFaliure(t.message!!)
                    }
                })
            } else if (document10thFile != null) {
                Retrofit(context!!).createWithAuthInterface().uploadDocuments(
                    SharedPreference(context!!).getProfile()?.id!!,
                    MultipartBody.Part.createFormData(
                        "document10",
                        document10thFile!!.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), document10thFile!!)
                    ),
                ).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            listener?.onSuccess(response.body()!!.message!!)
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        listener?.onFaliure(t.message!!)
                    }
                })
            }

        } else {
            val documentCertificate = MultipartBody.Part.createFormData(
                "documentCertificates",
                documentExperienceCertificateFile!!.name,
                RequestBody.create(
                    "image/*".toMediaTypeOrNull(),
                    documentExperienceCertificateFile!!
                )
            )
            if (documentGraduationFile != null) {
                Retrofit(context!!).createWithAuthInterface().uploadDocuments(
                    SharedPreference(context!!).getProfile()?.id!!,
                    MultipartBody.Part.createFormData(
                        "document10",
                        document10thFile!!.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), document10thFile!!)
                    ),
                    MultipartBody.Part.createFormData(
                        "document12",
                        document12thFile!!.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), document12thFile!!)
                    ),
                    MultipartBody.Part.createFormData(
                        "documentGraduation",
                        documentGraduationFile!!.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), documentGraduationFile!!)
                    ),
                    documentCertificate
                ).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            listener?.onSuccess(response.body()!!.message!!)
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        listener?.onFaliure(t.message!!)
                    }
                })
            } else if (document12thFile != null) {
                Retrofit(context!!).createWithAuthInterface().uploadDocuments(
                    SharedPreference(context!!).getProfile()?.id!!,
                    MultipartBody.Part.createFormData(
                        "document10",
                        document10thFile!!.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), document10thFile!!)
                    ),
                    MultipartBody.Part.createFormData(
                        "document12",
                        document12thFile!!.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), document12thFile!!)
                    ),
                    null,
                    documentCertificate
                ).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            listener?.onSuccess(response.body()!!.message!!)
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        listener?.onFaliure(t.message!!)
                    }
                })
            } else if (document10thFile != null) {
                Retrofit(context!!).createWithAuthInterface().uploadDocuments(
                    userId = SharedPreference(context!!).getProfile()?.id!!,
                    document10 =MultipartBody.Part.createFormData(
                        "document10",
                        document10thFile!!.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), document10thFile!!)
                    ),
                    documentCertificates = documentCertificate
                ).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            listener?.onSuccess(response.body()!!.message!!)
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        listener?.onFaliure(t.message!!)
                    }
                })
            }else {
                Retrofit(context!!).createWithAuthInterface().uploadDocuments(
                    SharedPreference(context!!).getProfile()?.id!!,
                    null,
                    null,
                    null,
                    documentCertificate
                ).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            listener?.onSuccess(response.body()!!.message!!)
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        listener?.onFaliure(t.message!!)
                    }
                })
            }
        }

    }
}