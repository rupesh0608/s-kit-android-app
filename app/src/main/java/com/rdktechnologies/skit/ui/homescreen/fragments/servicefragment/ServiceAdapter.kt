package com.rdktechnologies.skit.ui.homescreen.fragments.servicefragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.helperclasses.apiclasses.Services
import com.rdktechnologies.skit.ui.profilescreen.ProfileButtonModel
import com.rdktechnologies.skit.ui.profilescreen.subactivity.*

class ServiceAdapter(val list: ArrayList<Services>):RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.view.context).load(list[position].image).into(holder.img)
        holder.serviceName.text=list[position].name
        holder.view.setOnClickListener {
            val uri = Uri.parse(list[position].link)
            val intent=Intent(Intent.ACTION_VIEW)
            intent.setPackage("com.android.chrome")
            intent.data=uri
            holder.view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val view=itemView
        val img=itemView.findViewById<ImageView>(R.id.image)
        val serviceName=itemView.findViewById<TextView>(R.id.serviceName)
         init {

         }
    }
}
