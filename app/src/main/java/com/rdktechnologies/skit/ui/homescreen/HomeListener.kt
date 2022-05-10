package com.rdktechnologies.skit.ui.homescreen

import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment

interface HomeListener {

    fun onStarted()
    fun changeFragment(fragment: Fragment,id:String)
    fun goToProfileScreen()
}