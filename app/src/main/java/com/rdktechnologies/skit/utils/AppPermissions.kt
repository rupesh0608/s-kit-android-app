package com.rdktechnologies.skit.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class AppPermissions(var context: Context) {

    fun hasPermissions(vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

}