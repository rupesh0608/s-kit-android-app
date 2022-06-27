package com.rdktechnologies.skit.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rdktechnologies.skit.R

lateinit var alert:AlertDialog
fun Context.shortToast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}
fun ProgressBar.show(){
    visibility=View.VISIBLE
}
fun ProgressBar.gone(){
    visibility=View.GONE
}
fun LinearLayout.show(){
    visibility=View.VISIBLE
}
fun LinearLayout.gone(){
    visibility=View.GONE
}

fun Activity.showProgressAlert(){
        val alertDialog = AlertDialog.Builder(this)
        val views = layoutInflater.inflate(R.layout.progress_view, null)
        alertDialog.setView(views)
        alert = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
//    alert.window!!.setLayout(600, 400)
    alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
}
fun Activity.hideProgressAlert(){
    alert.dismiss()
}


