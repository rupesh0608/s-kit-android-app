package com.rdktechnologies.skit.ui.homescreen

import android.view.View
import androidx.lifecycle.ViewModel
import com.rdktechnologies.skit.ui.homescreen.fragments.bookmarkfragment.BookmarksFragment
import com.rdktechnologies.skit.ui.homescreen.fragments.govtexamsfragment.GovtExamsFragment
import com.rdktechnologies.skit.ui.homescreen.fragments.homefragment.HomeFragment
import com.rdktechnologies.skit.ui.homescreen.fragments.servicefragment.ServicesFragment
import com.rdktechnologies.skit.utils.Constants

class HomeViewModel : ViewModel() {

    var listener: HomeListener? = null

    fun onStarted() {
        listener?.onStarted()
        listener?.changeFragment(HomeFragment(),Constants.FRAGMENT_HOME)

    }

    fun onHomeClick(view: View) {
        listener?.changeFragment(HomeFragment(),Constants.FRAGMENT_HOME)
    }

    fun onGovtExamsClick(view: View) {
        listener?.changeFragment(GovtExamsFragment(),Constants.FRAGMENT_GOVT_EXAMS)
    }

    fun onServicesClick(view: View) {
        listener?.onStarted()
        listener?.changeFragment(ServicesFragment(),Constants.FRAGMENT_SERVICES)
    }

    fun onBookmarkClick(view: View) {
        listener?.onStarted()
        listener?.changeFragment(BookmarksFragment(),Constants.FRAGMENT_BOOKMARKS)
    }

    fun onProfileCLick(view: View) {
        listener?.goToProfileScreen()
    }
}