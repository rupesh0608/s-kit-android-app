package com.rdktechnologies.skit.ui.profilescreen.subactivity.editprofile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.databinding.ActivityEditProfileScreenBinding
import com.rdktechnologies.skit.helperclasses.apiclasses.LoginResponse
import com.rdktechnologies.skit.ui.permissioninfoscreen.PermissionInfoScreen
import com.rdktechnologies.skit.utils.*
import java.io.ByteArrayOutputStream
import java.io.File


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
        binding.progressView.visibility=View.GONE
     hideProgressAlert()
    }

    override fun onFailure(message: String) {
        hideProgressAlert()
        shortToast(message)
    }

    override fun onSuccess(response: LoginResponse) {
        hideProgressAlert()
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
        showProgressAlert()
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
                binding.imgProfile.setImageURI(selectedImageUri)
                binding.imgProfile.visibility= View.GONE
                val drawable= binding.imgProfile.drawable as BitmapDrawable
                val bitmap=drawable.bitmap
                val path=getRealPathFromURI(getImageUri(this,bitmap))
                val file=File(path)
                if(file.exists()){
                    viewModel.path=path
                    binding.imgProfile.visibility= View.VISIBLE
                    binding.imgProfile.setImageURI(selectedImageUri)
                }else{
                    shortToast("file does not exist")
                }
            }
        }
    }
    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "xyz", null)
        return Uri.parse(path)
    }

    private fun getRealPathFromURI(uri: Uri): String {
        var path = ""
        if (contentResolver != null) {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }
    inner class BackgroundTask(var ref: Activity, val bitmap: Bitmap) :
        AsyncTask<Int, Int, Void>() {

        override fun doInBackground(vararg params: Int?): Void? {
            val file = com.rdktechnologies.skit.helperclasses.File(ref)
            file.saveImage(bitmap)
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
        }

        override fun onCancelled(result: Void?) {
            super.onCancelled(result)

        }

        override fun onCancelled() {
            super.onCancelled()
        }
    }

}