package com.rdktechnologies.skit.ui.homescreen.fragments.homefragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.helperclasses.apiclasses.Course
import com.rdktechnologies.skit.helperclasses.apiclasses.CoursesResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.EligibleJobResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.Jobs
import com.rdktechnologies.skit.ui.morejobscoursesscreen.MoreJobsCoursesActivity
import com.rdktechnologies.skit.ui.profilescreen.ProfileScreen
import com.rdktechnologies.skit.utils.SharedPreference
import com.rdktechnologies.skit.utils.hideProgressAlert
import com.rdktechnologies.skit.utils.showProgressAlert
import com.technicalrupu.sportsapp.HelperClasses.Api.MyApi
import com.technicalrupu.sportsapp.HelperClasses.Api.UdemyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class HomeFragment : Fragment() {
    lateinit var recyclerview: RecyclerView
    lateinit var txtGm: TextView
    lateinit var cardSearch:CardView
    lateinit var edtSearch:EditText
    lateinit var jobTab: TextView
    lateinit var coursesTab:TextView
    lateinit var showAll:TextView
    lateinit var heading1:TextView
    var activeTab=1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        initializeListeners()
        return view
    }

private fun initializeListeners(){
    cardSearch.setOnClickListener{
        if(activeTab==1){
            openMoreJobsCoursesPage("job")
        }else{
            openMoreJobsCoursesPage("course")
        }

    }
    edtSearch.setOnClickListener {
        if(activeTab==1){
            openMoreJobsCoursesPage("job")
        }else{
            openMoreJobsCoursesPage("course")
        }
    }
    showAll.setOnClickListener {
        if(activeTab==1){
            openMoreJobsCoursesPage("job")
        }else{
            openMoreJobsCoursesPage("course")
        }
    }
    jobTab.setOnClickListener {
        activeTab=1
        edtSearch.hint="Search Jobs..."
        coursesTab.setBackgroundColor(resources.getColor(R.color.white))
        coursesTab.setTextColor(resources.getColor(R.color.end_color))
        jobTab.setBackgroundColor(resources.getColor(R.color.end_color))
        jobTab.setTextColor(resources.getColor(R.color.white))
        getRecentJobs()
    }
    coursesTab.setOnClickListener {
        activeTab=2
        edtSearch.hint="Search Courses..."
        coursesTab.setBackgroundColor(resources.getColor(R.color.end_color))
        coursesTab.setTextColor(resources.getColor(R.color.white))
        jobTab.setBackgroundColor(resources.getColor(R.color.white))
        jobTab.setTextColor(resources.getColor(R.color.end_color))
        getRecommendedCourses()
    }
}
    private fun openMoreJobsCoursesPage(value:String){
        val i=Intent(requireActivity(), MoreJobsCoursesActivity::class.java)
        i.putExtra("resource",value)
        startActivity(i)
    }
    fun init(view: View) {
        recyclerview = view.findViewById(R.id.recyclerView)
        recyclerview.visibility=View.VISIBLE
        txtGm = view.findViewById<TextView>(R.id.txtGm)
        cardSearch=view.findViewById(R.id.cardSearch)
        edtSearch=view.findViewById(R.id.edtSearch)
        jobTab=view.findViewById(R.id.jobTab)
        coursesTab=view.findViewById(R.id.coursesTab)
        txtGm.text=setWishing()
        heading1=view.findViewById(R.id.heading1)
        showAll=view.findViewById(R.id.showAll)
        edtSearch.hint="Search Jobs..."
        getRecentJobs()
    }

    private fun getRecentJobs(){
        heading1.text="Recent Jobs"
        activity?.showProgressAlert()
//        recyclerview.visibility=View.GONE
        MyApi().getAllEligibleJobs(SharedPreference(requireActivity()).getLoginResponse()!!.data!!.id!!).enqueue(object : Callback<EligibleJobResponse> {
            override fun onResponse(
                call: Call<EligibleJobResponse>,
                response: Response<EligibleJobResponse>
            ) {
                if (response.isSuccessful && response.body()!=null) {
                    if(response.body()!!.error!=true) {
                        val adapter = JobsAdapter(response.body()!!.data!! as ArrayList<Jobs>,7)
                        recyclerview.layoutManager = LinearLayoutManager(activity)
//                        recyclerview.visibility=View.VISIBLE
                        recyclerview.adapter = adapter
                        activity?.hideProgressAlert()
                    }else{
                        activity?.hideProgressAlert()
                        Toast.makeText(requireActivity(),"Something went wrong...", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<EligibleJobResponse>, t: Throwable) {
                activity?.hideProgressAlert()
                Toast.makeText(requireActivity(),t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getRecommendedCourses(){
        heading1.text="Recommended Courses"
//        recyclerview.visibility=View.GONE
        activity?.showProgressAlert()
        UdemyApi().getRecommendedCourses().enqueue(object : Callback<CoursesResponse> {
            override fun onResponse(
                call: Call<CoursesResponse>,
                response: Response<CoursesResponse>
            ) {
                if (response.isSuccessful && response.body()!=null) {
                        val adapter =CourseAdapter(response.body()!!.results as ArrayList<Course>,7)
                        recyclerview.layoutManager = LinearLayoutManager(activity)
//                        recyclerview.visibility=View.VISIBLE
                        recyclerview.adapter = adapter
                    activity?.hideProgressAlert()

                }
            }

            override fun onFailure(call: Call<CoursesResponse>, t: Throwable) {
                activity?.hideProgressAlert()
                Toast.makeText(requireActivity(),t.message, Toast.LENGTH_SHORT).show()
            }
        })
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