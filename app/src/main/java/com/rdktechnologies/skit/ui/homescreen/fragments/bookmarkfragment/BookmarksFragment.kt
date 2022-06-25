package com.rdktechnologies.skit.ui.homescreen.fragments.bookmarkfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.ui.homescreen.fragments.homefragment.JobsAdapter
import com.rdktechnologies.skit.ui.profilescreen.ProfileButtonModel

class BookmarksFragment : Fragment() {
    lateinit var recyclerview: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_bookmarks, container, false)
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
//        val adapter = JobsAdapter(data)
//        recyclerview.layoutManager = LinearLayoutManager(activity)
//        recyclerview.adapter = adapter

        return view
    }
    fun init(view: View) {
        recyclerview =view.findViewById(R.id.bookmarkRecyclerView)
    }

}