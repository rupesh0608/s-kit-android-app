package com.rdktechnologies.skit.ui.homescreen.fragments.servicefragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.ui.profilescreen.ProfileButtonModel
import com.rdktechnologies.skit.ui.profilescreen.subactivity.*

class ServiceAdapter(val list: ArrayList<ProfileButtonModel>):RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.img.setImageDrawable(holder.view.context.getDrawable(list[0].icon))
    }

    override fun getItemCount(): Int {
        return 20
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val view=itemView
        val img=itemView.findViewById<ImageView>(R.id.image)
         init {

         }
    }
}
