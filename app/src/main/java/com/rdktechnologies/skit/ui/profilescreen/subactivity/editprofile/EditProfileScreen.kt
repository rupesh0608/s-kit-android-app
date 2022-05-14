package com.rdktechnologies.skit.ui.profilescreen.subactivity.editprofile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.databinding.ActivityEditProfileScreenBinding
import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse
import com.rdktechnologies.skit.ui.permissioninfoscreen.PermissionInfoScreen
import com.rdktechnologies.skit.utils.*


class EditProfileScreen : AppCompatActivity(), EditProfileListener {
    lateinit var binding: ActivityEditProfileScreenBinding
    lateinit var viewModel: EditProfileViewModel
    var permissions = arrayOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_edit_profile_screen
            ) as ActivityEditProfileScreenBinding
        viewModel = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)
        permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        binding.editProfileViewModel = viewModel
        viewModel.editProfileListener = this
        onStarted()
    }

    @SuppressLint("SetTextI18n")
    override fun onStarted() {
        if (SharedPreference(this).getProfile()?.picUrl == null) {
            Glide.with(this).load("https://source.unsplash.com/user/c_v_r/100x100")
                .into(binding.imgProfile)
        } else {
            Glide.with(this).load(SharedPreference(this).getProfile()?.picUrl)
                .into(binding.imgProfile)
        }
        val profile = SharedPreference(this).getLoginResponse()?.data
        viewModel.firstName = profile?.firstName
        viewModel.lastName = profile?.lastName
        viewModel.email = profile?.email
        viewModel.context=this
        viewModel.phoneNumber=profile?.phoneNumber.toString()
        binding.progressView.gone()
    }

    override fun onFailure(message: String) {
        binding.progressView.gone()
        shortToast(message)
    }

    override fun onSuccess(response: LoginResponse) {
        binding.progressView.gone()
        shortToast(response.message!!)
    }

    override fun selectImageFromGallery() {
        if (!AppPermissions(this).hasPermissions(*permissions))
        {
            val intent = Intent(this, PermissionInfoScreen::class.java)
            startActivity(intent)
        }else{
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 101)
        }

    }

    override fun showProgress() {
        binding.progressView.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(!(grantResults.isNotEmpty() && (grantResults[0]+grantResults[1]== PackageManager.PERMISSION_GRANTED)))
        {
            val intent = Intent(this, PermissionInfoScreen::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                val selectedImageUri: Uri? = data?.data
                var path:String?=null
                val pathCol = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(selectedImageUri!!, pathCol, null, null, null)
                if (cursor != null) {
                    cursor.moveToFirst()
                    val colIndex = cursor.getColumnIndex(pathCol[0])
                    path = cursor.getString(colIndex)
                    cursor.close()
                }
                viewModel.path=path
                if (null != selectedImageUri) {
                    binding.imgProfile.setImageURI(selectedImageUri)
                }
            }
        }
    }

}