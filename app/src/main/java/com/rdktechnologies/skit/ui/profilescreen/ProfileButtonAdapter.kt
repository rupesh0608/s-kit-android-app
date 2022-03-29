package com.rdktechnologies.skit.ui.profilescreen

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.ui.profilescreen.subactivity.*

class ProfileButtonAdapter(var activity: Activity, val list: ArrayList<ProfileButtonModel>):RecyclerView.Adapter<ProfileButtonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ittem_setting_buttons, parent, false)

        return ViewHolder(view,activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.text.text = item.text
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View, var activity:Activity) : RecyclerView.ViewHolder(ItemView) {
        val text=itemView.findViewById<TextView>(R.id.text)

         init {
             itemView.setOnClickListener{
                 when(adapterPosition){
                     0-> openEditProfileScreen()
                     1->openUploadDocumentsScreen()
                     2->openVerificationHistoryScreen()
                     3->openResumeScreen()
                     4->openChangePasswordScreen()
                     5->openAboutScreen()
                     6->logout()
                 }
             }
         }

        private fun logout() {
            Toast.makeText( itemView.context,"logout",Toast.LENGTH_SHORT).show()
            activity.finish()
        }

        private fun openAboutScreen() {
            activity.startActivity(Intent(activity,AboutScreen::class.java))
        }

        private fun openChangePasswordScreen() {
            activity.startActivity(Intent(activity,ChangePasswordScreen::class.java))
        }

        private fun openResumeScreen() {
            activity.startActivity(Intent(activity,ResumeScreen::class.java))
        }

        private fun openVerificationHistoryScreen() {
            activity.startActivity(Intent(activity,VerificationHistoryScreen::class.java))
        }

        private fun openUploadDocumentsScreen() {
            activity.startActivity(Intent(activity,UploadDocumentsScreen::class.java))
        }

        private fun openEditProfileScreen() {
            activity.startActivity(Intent(activity,EditProfileScreen::class.java))
        }

    }
}
