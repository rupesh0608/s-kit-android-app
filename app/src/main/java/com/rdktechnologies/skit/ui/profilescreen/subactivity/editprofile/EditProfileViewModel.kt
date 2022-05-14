package com.rdktechnologies.skit.ui.profilescreen.subactivity.editprofile

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse
import com.rdktechnologies.skit.utils.SharedPreference
import com.technicalrupu.sportsapp.HelperClasses.Retrofit
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditProfileViewModel : ViewModel() {

    var firstName: String? = ""
    var lastName: String? = ""
    var email: String? = ""
    var phoneNumber: String? = null
    var editProfileListener: EditProfileListener? = null
    var context: Context? = null
    var path: String? = null

    fun onEditPicClicked(view: View) {
        editProfileListener?.selectImageFromGallery()
    }

    fun onUpdateClicked(view: View) {
        editProfileListener?.showProgress()
        var image:MultipartBody.Part?= null
        try {
            if(path!=null) {
                val file =
                    RequestBody.create(
                        "multipart/form-data".toMediaTypeOrNull(),
                        File(path.toString())
                    )
                image =
                    MultipartBody.Part.createFormData(
                        "profilePic",
                        File(path.toString()).name,
                        file
                    )
            }else{
                editProfileListener?.onFailure("please Select Image to update Profile")
                return
            }
            Retrofit(context!!).createWithAuthInterface().editProfile(
                firstName!!, lastName!!,
                SharedPreference(context!!).getProfile()?.id!!,
                email!!,
                phoneNumber?.toLong(),
                image
            ).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                       SharedPreference(context!!).setProfile(response.body()!!.data!!)
                        firstName=response.body()!!.data?.firstName
                        lastName=response.body()!!.data?.lastName
                        email=response.body()!!.data?.email
                        phoneNumber=response.body()!!.data?.phoneNumber.toString()
                        editProfileListener?.onSuccess(response.body() as LoginResponse)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    editProfileListener?.onFailure(t.message!!)
                }
            })
        } catch (e: Exception) {
            editProfileListener?.onFailure(e.message!!)
        }
    }

}