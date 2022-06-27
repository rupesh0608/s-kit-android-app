package com.rdktechnologies.skit.ui.homescreen.fragments.bookmarkfragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.helperclasses.apiclasses.Course
import com.rdktechnologies.skit.helperclasses.apiclasses.CoursesResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.EligibleJobResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.Jobs
import com.rdktechnologies.skit.ui.homescreen.fragments.homefragment.CourseAdapter
import com.rdktechnologies.skit.ui.homescreen.fragments.homefragment.JobsAdapter
import com.rdktechnologies.skit.ui.profilescreen.ProfileButtonModel
import com.rdktechnologies.skit.utils.SharedPreference
import com.rdktechnologies.skit.utils.hideProgressAlert
import com.rdktechnologies.skit.utils.showProgressAlert
import com.technicalrupu.sportsapp.HelperClasses.Api.MyApi
import com.technicalrupu.sportsapp.HelperClasses.Api.UdemyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarksFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var jobTab: TextView
    private lateinit var edtSearch:EditText
    private lateinit var coursesTab: TextView
    private var jobList= mutableListOf<Jobs>()
    private var courseList= mutableListOf<Course>()
    private var activeTab=1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_bookmarks, container, false)
        init(view)
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                filter()
            }
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
        return view
    }

    private fun initializeListeners(){
        jobTab.setOnClickListener {
            if(activeTab!=1) {
                activeTab = 1
                edtSearch.hint = "Search Bookmarked Jobs..."
                coursesTab.setBackgroundColor(resources.getColor(R.color.white))
                coursesTab.setTextColor(resources.getColor(R.color.end_color))
                jobTab.setBackgroundColor(resources.getColor(R.color.end_color))
                jobTab.setTextColor(resources.getColor(R.color.white))
                getBookmarkedJobs()
            }

        }
        coursesTab.setOnClickListener {
            if(activeTab!=2) {
                activeTab = 2
                edtSearch.hint = "Search Bookmarked Courses..."
                coursesTab.setBackgroundColor(resources.getColor(R.color.end_color))
                coursesTab.setTextColor(resources.getColor(R.color.white))
                jobTab.setBackgroundColor(resources.getColor(R.color.white))
                jobTab.setTextColor(resources.getColor(R.color.end_color))
                getBookmarkedCourses()
            }
        }
    }

    fun filter(){
        val text=edtSearch.text.toString()
        if(text.isEmpty() ||text.isBlank() ){
            if(activeTab==1){
                loadJobRecyclerView(jobList as ArrayList<Jobs>)
            }else{
                loadCourseRecyclerView(courseList as ArrayList<Course>)
            }
        }else{
            if(activeTab==1){
                val filteredJobList= jobList.filter {
                        it -> it.postName.contains(text,true)
                }
                loadJobRecyclerView(filteredJobList as ArrayList<Jobs>)
            }else{
                val filteredCourseList=courseList!!.filter {
                        it -> it.title.contains(text,true)
                }
                loadCourseRecyclerView(filteredCourseList as ArrayList<Course>)
            }
        }


    }
    fun init(view: View) {
        recyclerView =view.findViewById(R.id.recyclerView)
        edtSearch=view.findViewById(R.id.edtSearch)
        jobTab=view.findViewById(R.id.jobTab)
        coursesTab=view.findViewById(R.id.coursesTab)
        edtSearch.hint="Search Bookmarked Jobs..."
        initializeListeners()
        getBookmarkedJobs()
    }
    private fun getBookmarkedJobs(){
        activity?.showProgressAlert()
        MyApi().getAllEligibleJobs(SharedPreference(requireActivity()).getLoginResponse()!!.data!!.id!!).enqueue(object :
            Callback<EligibleJobResponse> {
            override fun onResponse(
                call: Call<EligibleJobResponse>,
                response: Response<EligibleJobResponse>
            ) {
                if (response.isSuccessful && response.body()!=null) {
                    if(response.body()!!.error!=true) {
                        jobList= (response.body()!!.data as MutableList<Jobs>?)!!
                        activity?.hideProgressAlert()
                        loadJobRecyclerView(response.body()!!.data!! as java.util.ArrayList<Jobs>)
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
    private fun getBookmarkedCourses(){
        activity?.showProgressAlert()
     UdemyApi().getRecommendedCourses().enqueue(object :
            Callback<CoursesResponse> {
            override fun onResponse(
                call: Call<CoursesResponse>,
                response: Response<CoursesResponse>
            ) {
                if (response.isSuccessful && response.body()!=null) {
                        courseList= (response.body()!!.results as MutableList<Course>?)!!
                    activity?.hideProgressAlert()
                        loadCourseRecyclerView(response.body()!!.results!! as ArrayList<Course>)
                }else{
                    activity?.hideProgressAlert()
                    Toast.makeText(requireActivity(),"Something went wrong...", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CoursesResponse>, t: Throwable) {
                activity?.hideProgressAlert()
                Toast.makeText(requireActivity(),t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun loadCourseRecyclerView(list:ArrayList<Course>){
        activity?.showProgressAlert()
        val adapter =
            CourseAdapter(list,list.size)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.visibility=View.VISIBLE
        recyclerView.adapter = adapter
        activity?.hideProgressAlert()
    }
    fun loadJobRecyclerView(list:ArrayList<Jobs>){
        activity?.showProgressAlert()
        val adapter =
            JobsAdapter(list,list.size)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.visibility=View.VISIBLE
        recyclerView.adapter = adapter
        activity?.hideProgressAlert()
    }

}