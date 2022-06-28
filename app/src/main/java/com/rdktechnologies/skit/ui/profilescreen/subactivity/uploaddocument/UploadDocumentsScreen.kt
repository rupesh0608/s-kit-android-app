package com.rdktechnologies.skit.ui.profilescreen.subactivity.uploaddocument

import android.Manifest
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.databinding.ActivityUploadDocumentsScreenBinding
import com.rdktechnologies.skit.ui.homescreen.HomeScreen
import com.rdktechnologies.skit.ui.permissioninfoscreen.PermissionInfoScreen
import com.rdktechnologies.skit.ui.profilescreen.subactivity.editprofile.EditProfileScreen
import com.rdktechnologies.skit.utils.*
import java.io.ByteArrayOutputStream
import java.io.File


class UploadDocumentsScreen : AppCompatActivity(), UploadDocumentsListener {
    lateinit var binding: ActivityUploadDocumentsScreenBinding
    lateinit var viewModel: UploadDocumentsViewModel
    var permissions = arrayOf<String>()
    lateinit var output: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_upload_documents_screen
            ) as ActivityUploadDocumentsScreenBinding
        viewModel = ViewModelProviders.of(this).get(UploadDocumentsViewModel::class.java)
        viewModel.context=this
        permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        binding.uploadDocumentsViewModel = viewModel
        viewModel.listener = this
        onStarted()
    }

    override fun openGallery(requestCode: Int) {
        if (!AppPermissions(this).hasPermissions(*permissions)) {
            val intent = Intent(this, PermissionInfoScreen::class.java)
            startActivity(intent)
        } else {
            startActivityForResult(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                ), requestCode
            )
        }
    }

    override fun onStarted() {
        if (binding.scanned10.visibility == View.VISIBLE) {
            binding.scanned10.visibility = View.GONE
        }else{
            binding.scanned10.visibility = View.GONE
        }
        if (binding.scanned12.visibility == View.VISIBLE) {
            binding.scanned12.visibility = View.GONE
        }else{
            binding.scanned12.visibility = View.GONE
        }
        if (binding.scannedGraduation.visibility == View.VISIBLE) {
            binding.scannedGraduation.visibility = View.GONE
        }else{
            binding.scannedGraduation.visibility = View.GONE
        }
        if (binding.scannedCertificates.visibility == View.VISIBLE) {
            binding.scannedCertificates.visibility = View.GONE
        }else{
            binding.scannedCertificates.visibility = View.GONE
        }
    }

    override fun openCamera(requestCode: Int) {
        if (!AppPermissions(this).hasPermissions(*permissions)) {
            val intent = Intent(this, PermissionInfoScreen::class.java)
            startActivity(intent)
        } else {
            val camera_intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera_intent, requestCode)
        }

    }

    override fun onFaliure(message: String) {
        hideProgressAlert()
        shortToast(message)
    }

    override fun onSuccess(message: String) {
        hideProgressAlert()
        shortToast(message)
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()

    }

    override fun showProgress() {
        showProgressAlert()
    }

    override fun hideProgress() {
        hideProgressAlert()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                Constants.UPLOAD_DOCUMENT_10th_CAMERA -> {
                    val bitmap = data!!.extras!!.get("data") as Bitmap
                    if (binding.scanned10.visibility != View.VISIBLE)
                        binding.scanned10.visibility = View.VISIBLE
                    binding.scanned10.setImageBitmap(bitmap)
                    val drawable= binding.scanned10.drawable as BitmapDrawable
                    if(drawable.bitmap!=null)
                    viewModel.document10thFile=getFile(drawable.bitmap)
                }
                Constants.UPLOAD_DOCUMENT_10th_GALLERY -> {
                    val imageData = getResultantImagePath(data!!)
                    if (binding.scanned10.visibility != View.VISIBLE)
                        binding.scanned10.visibility = View.VISIBLE

                    binding.scanned10.setImageURI(imageData.uri)
                    val drawable= binding.scanned10.drawable as BitmapDrawable
                    if(drawable.bitmap!=null)
                    viewModel.document10thFile=getFile(drawable.bitmap)
                }
                Constants.UPLOAD_DOCUMENT_12th_CAMERA -> {
                    val bitmap = data!!.extras!!.get("data") as Bitmap
                    binding.scanned12.visibility = View.VISIBLE
                    binding.scanned12.setImageBitmap(bitmap)
                    val drawable= binding.scanned12.drawable as BitmapDrawable
                    if(drawable.bitmap!=null)
                    viewModel.document12thFile=getFile(drawable.bitmap)
                }
                Constants.UPLOAD_DOCUMENT_12th_GALLERY -> {
                    val imageData = getResultantImagePath(data!!)
                    binding.scanned12.visibility = View.VISIBLE
                    binding.scanned12.setImageURI(imageData.uri)
                    val drawable= binding.scanned12.drawable as BitmapDrawable
                    if(drawable.bitmap!=null)
                    viewModel.document12thFile=getFile(drawable.bitmap)
                }
                Constants.UPLOAD_DOCUMENT_GRADUATION_CAMERA -> {
                    val bitmap = data!!.extras!!.get("data") as Bitmap
                    binding.scannedGraduation.visibility = View.VISIBLE
                    binding.scannedGraduation.setImageBitmap(bitmap)
                    val drawable= binding.scannedGraduation.drawable as BitmapDrawable
                    if(drawable.bitmap!=null)
                    viewModel.documentGraduationFile=getFile(drawable.bitmap)
                }
                Constants.UPLOAD_DOCUMENT_GRADUATION_GALLERY -> {
                    val imageData = getResultantImagePath(data!!)
                    binding.scannedGraduation.visibility = View.VISIBLE
                    binding.scannedGraduation.setImageURI(imageData.uri)
                    val drawable= binding.scannedGraduation.drawable as BitmapDrawable
                    if(drawable.bitmap!=null)
                    viewModel.documentGraduationFile=getFile(drawable.bitmap)
                }
                Constants.UPLOAD_DOCUMENT_CERTIFICATE_CAMERA -> {
                    val bitmap = data!!.extras!!.get("data") as Bitmap
                    binding.scannedCertificates.visibility = View.VISIBLE
                    binding.scannedCertificates.setImageBitmap(bitmap)
                    val drawable= binding.scannedCertificates.drawable as BitmapDrawable
                    viewModel.documentExperienceCertificateFile=getFile(drawable.bitmap)
                }
                Constants.UPLOAD_DOCUMENT_CERTIFICATE_GALLERY -> {
                    val imageData = getResultantImagePath(data!!)
                    binding.scannedCertificates.visibility = View.VISIBLE
                    binding.scannedCertificates.setImageURI(imageData.uri)
                    val drawable= binding.scannedCertificates.drawable as BitmapDrawable

                }
            }
        }
    }

    private fun getFile(bitmap:Bitmap):File?{
        val path=getRealPathFromURI(getImageUri(this,bitmap))
        val file=File(path)
        return if(file.exists()){
            file
        }else{
            null
        }
    }
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
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

    private fun getResultantImagePath(data: Intent): ImageData {
        val selectedImageUri: Uri? = data?.data
        val imageData = ImageData()
        val pathCol = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(selectedImageUri!!, pathCol, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            val colIndex = cursor.getColumnIndex(pathCol[0])
            imageData.path = cursor.getString(colIndex)
            cursor.close()
        }
        if (null != selectedImageUri) {
            imageData.uri = selectedImageUri
        }
        return imageData
    }
}