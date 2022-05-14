package com.rdktechnologies.skit.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rdktechnologies.skit.R

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


