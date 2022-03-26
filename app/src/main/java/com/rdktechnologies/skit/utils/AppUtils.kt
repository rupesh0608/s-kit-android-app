package com.rdktechnologies.skit.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

class AppUtils {

    fun showToast(message: String, context: Context) {
        try {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun closeKeyboard(activity: Activity) {
        try {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun showKeyboard(activity: Activity) {
        try {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                InputMethodManager.SHOW_FORCED,
                0
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}