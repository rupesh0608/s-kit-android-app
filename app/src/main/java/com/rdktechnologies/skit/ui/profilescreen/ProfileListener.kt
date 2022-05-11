package com.rdktechnologies.skit.ui.profilescreen

interface ProfileListener {
    fun setDetails()
    fun loadRecyclerView(data:ArrayList<ProfileButtonModel>)
}