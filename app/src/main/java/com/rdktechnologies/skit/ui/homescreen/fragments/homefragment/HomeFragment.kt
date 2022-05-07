package com.rdktechnologies.skit.ui.homescreen.fragments.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.ui.profilescreen.ProfileButtonModel
import com.rdktechnologies.skit.utils.SharedPreference
import java.util.*


class HomeFragment : Fragment() {

    lateinit var recyclerview: RecyclerView
    lateinit var txtGm: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        val data = ArrayList<ProfileButtonModel>()
        data.add(ProfileButtonModel(text = "Edit Profile", icon = R.drawable.ic_edit_profile))
        data.add(
            ProfileButtonModel(
                text = "Upload Documents",
                icon = R.drawable.ic_upload_document_icon
            )
        )
        data.add(
            ProfileButtonModel(
                text = "Verification History",
                icon = R.drawable.ic_verification_history_icon
            )
        )
        data.add(
            ProfileButtonModel(
                text = "Download Resume",
                icon = R.drawable.ic_download_resume_icon
            )
        )
        data.add(
            ProfileButtonModel(
                text = "Change Password",
                icon = R.drawable.ic_change_password_icon
            )
        )
        data.add(ProfileButtonModel(text = "About", icon = R.drawable.ic_about_icon))
        data.add(ProfileButtonModel(text = "Logout", icon = R.drawable.ic_logout_icon))
        val adapter = JobsAdapter(data)
        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.adapter = adapter

        return view
    }

    fun init(view: View) {
        recyclerview = view.findViewById(R.id.home_recyclerView)
        txtGm = view.findViewById<TextView>(R.id.txtGm)
        txtGm.text=setWishing()
    }

    private fun setWishing(): String {
        val dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        var message: String? = null
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)
        if (timeOfDay in 0..11) {
            message = "Good Morning"
        } else if (timeOfDay in 12..15) {
            message = "Good Afternoon"
        } else if (timeOfDay in 16..20) {
            message = "Good Evening"
        } else if (timeOfDay in 21..23) {
            message = "Good Night"
        }
        return "${message!!} ${SharedPreference(requireContext()).getProfile()?.firstName}"
    }
}