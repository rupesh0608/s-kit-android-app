package com.rdktechnologies.skit.ui.homescreen.fragments.servicefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.ui.homescreen.fragments.homefragment.JobsAdapter
import com.rdktechnologies.skit.ui.profilescreen.ProfileButtonModel


class ServicesFragment : Fragment() {

    lateinit var recyclerview: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_services, container, false)
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
        val adapter = ServiceAdapter(data)
        recyclerview.layoutManager = GridLayoutManager(activity,3)
        recyclerview.adapter = adapter

        return view
    }

    fun init(view: View) {
        recyclerview =view.findViewById(R.id.serviceRecyclerView)
    }


}