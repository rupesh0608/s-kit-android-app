package com.rdktechnologies.skit.ui.homescreen.fragments.govtexamsfragment

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.ui.profilescreen.ProfileButtonModel

class GovtExamsAdapter(val list: ArrayList<ProfileButtonModel>):RecyclerView.Adapter<GovtExamsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_govt_exams, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text=list[position].text
        holder.cdLayout.startAnimation(
            AnimationUtils.loadAnimation(
                holder.view.context, R.anim.animation_fall_down
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cdLayout=itemView.findViewById<CardView>(R.id.cdLayout)
        val text=itemView.findViewById<TextView>(R.id.text)
        val image=itemView.findViewById<ImageView>(R.id.image)
        val view=itemView
         init {

         }
    }
}
