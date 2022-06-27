package com.rdktechnologies.skit.helperclasses

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.rdktechnologies.skit.R
import java.io.*
import java.io.File
import java.lang.Exception
import java.util.*


class File(val ref: Context) {
    fun saveImage(bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT >= 29) {
            val values = contentValues()
            values.put(
                MediaStore.Images.Media.RELATIVE_PATH,
                "Pictures/"+ref.getString(R.string.app_name)
            )
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            val uri: Uri? =
                ref.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                try {
                    saveImageToStream(bitmap, ref.contentResolver.openOutputStream(uri))
                    values.put(MediaStore.Images.Media.IS_PENDING, false)
                    ref.contentResolver.update(uri, values, null, null)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        } else {
            val directory = java.io.File(
                Environment.getExternalStorageDirectory()
                    .toString() + '/'+ref.getString(R.string.app_name)
            )
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val fileName = System.currentTimeMillis().toString() + ".png"
            val file = File(directory, fileName)
            try {
                saveImageToStream(bitmap, FileOutputStream(file))
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath())
                ref.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun contentValues(): ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        }
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}