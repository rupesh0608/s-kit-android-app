package com.rdktechnologies.skit.ui.homescreen.fragments.homefragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.helperclasses.apiclasses.Course
import com.rdktechnologies.skit.helperclasses.apiclasses.Jobs

class CourseAdapter(val list: ArrayList<Course>, private val size: Int) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_courses, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text =list[position].title
        holder.headLine.text = list[position].headline
        Glide.with(holder.view.context).load(list[position].image_125_H).into(holder.image)
        holder.llLayout.startAnimation(
            AnimationUtils.loadAnimation(
                holder.view.context, R.anim.animation_fall_down
            )
        )
        holder.view.setOnClickListener {
            val uri = Uri.parse("https://www.udemy.com${list[position].url}")
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setPackage("com.android.chrome")
            intent.data = uri
            holder.view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val view = itemView
        val title = itemView.findViewById<TextView>(R.id.title)
        val headLine = itemView.findViewById<TextView>(R.id.headline)
        val llLayout=itemView.findViewById<LinearLayout>(R.id.llLayout)
        val image=itemView.findViewById<ImageView>(R.id.image)

        init {

        }
    }
}
