package com.rdktechnologies.skit.ui.homescreen.fragments.homefragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.helperclasses.apiclasses.Jobs

class JobsAdapter(val list: ArrayList<Jobs>) : RecyclerView.Adapter<JobsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_jobs, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.postName.text = list[position].postName
        holder.boardName.text = list[position].boardName
        holder.qualifications.text = list[position].qualifications
        holder.llLayout.startAnimation(
            AnimationUtils.loadAnimation(
                holder.view.context, R.anim.animation_fall_down
            )
        )
        holder.view.setOnClickListener {
            val uri = Uri.parse(list[position].link)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setPackage("com.android.chrome")
            intent.data = uri
            holder.view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val view = itemView
        val postName = itemView.findViewById<TextView>(R.id.postName)
        val boardName = itemView.findViewById<TextView>(R.id.boardName)
        val qualifications = itemView.findViewById<TextView>(R.id.qualifications)
        val llLayout=itemView.findViewById<LinearLayout>(R.id.llLayout)

        init {

        }
    }
}
