package com.rdktechnologies.skit.ui.morejobscoursesscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.helperclasses.apiclasses.Course
import com.rdktechnologies.skit.helperclasses.apiclasses.CoursesResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.EligibleJobResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.Jobs
import com.rdktechnologies.skit.ui.homescreen.fragments.homefragment.CourseAdapter
import com.rdktechnologies.skit.ui.homescreen.fragments.homefragment.JobsAdapter
import com.rdktechnologies.skit.utils.Constants
import com.rdktechnologies.skit.utils.SharedPreference
import com.rdktechnologies.skit.utils.hideProgressAlert
import com.rdktechnologies.skit.utils.showProgressAlert
import com.technicalrupu.sportsapp.HelperClasses.Api.MyApi
import com.technicalrupu.sportsapp.HelperClasses.Api.UdemyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MoreJobsCoursesActivity : AppCompatActivity() {

    lateinit var edtSearch:EditText
    lateinit var cardSearch:CardView
    lateinit var recyclerview:RecyclerView
    lateinit var type:String
    private var jobList= mutableListOf<Jobs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_jobs_courses)
        init()
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                filterOrSearch()
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
    }
    fun init(){
        edtSearch=findViewById(R.id.edtSearch)
        edtSearch=findViewById(R.id.edtSearch)
        cardSearch=findViewById(R.id.cardSearch)
        recyclerview=findViewById(R.id.recyclerView)
        type= intent.getStringExtra("resource").toString()
        if(type=="job"){
            edtSearch.hint="Search Jobs..."
            getJobs()
        }else{
            edtSearch.hint="Search Courses..."
            searchCoursesWithoutQuery()
        }
    }
    fun filterOrSearch(){
       val searchText= edtSearch.text.toString()
        if(type=="job"){
            if(searchText.isEmpty()||searchText.isBlank()) {
                getJobs()

            }else{
                val filteredJobList = jobList.filter { it ->
                    it.postName.contains(searchText, true)
                }
                loadJobs(filteredJobList as ArrayList<Jobs>)
            }
        }else{
            if(searchText.isEmpty()||searchText.isBlank())
              searchCoursesWithoutQuery()
            else
                searchCoursesWithQuery(searchText)
        }
    }
    private fun loadJobs(list:ArrayList<Jobs>){
        showProgressAlert()
        val adapter = JobsAdapter(list,list.size)
        recyclerview.layoutManager = LinearLayoutManager(this@MoreJobsCoursesActivity)
        recyclerview.adapter = adapter
        hideProgressAlert()
    }
    private fun getJobs(){
       showProgressAlert()
        MyApi().getAllEligibleJobs(SharedPreference(this).getLoginResponse()!!.data!!.id!!).enqueue(object : Callback<EligibleJobResponse> {
            override fun onResponse(
                call: Call<EligibleJobResponse>,
                response: Response<EligibleJobResponse>
            ) {
                if (response.isSuccessful && response.body()!=null) {
                    if(response.body()!!.error!=true) {
                        jobList= response.body()!!.data as MutableList<Jobs>
                        hideProgressAlert()
                        loadJobs(jobList as ArrayList<Jobs>)
                    }else{
                        hideProgressAlert()
                        Toast.makeText(this@MoreJobsCoursesActivity,"Something went wrong...", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<EligibleJobResponse>, t: Throwable) {
                hideProgressAlert()
                Toast.makeText(this@MoreJobsCoursesActivity,t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun searchCoursesWithoutQuery(){
        showProgressAlert()
        UdemyApi().getRecommendedCourses().enqueue(object : Callback<CoursesResponse> {
            override fun onResponse(
                call: Call<CoursesResponse>,
                response: Response<CoursesResponse>
            ) {
                if (response.isSuccessful && response.body()!=null) {
                    val adapter = CourseAdapter(response.body()!!.results as ArrayList<Course>,response.body()!!.results.size)
                    recyclerview.layoutManager = LinearLayoutManager(this@MoreJobsCoursesActivity)
                    recyclerview.adapter = adapter
                    hideProgressAlert()

                }
            }

            override fun onFailure(call: Call<CoursesResponse>, t: Throwable) {
                hideProgressAlert()
                Toast.makeText(this@MoreJobsCoursesActivity,t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun searchCoursesWithQuery(text:String){
        val query="${Constants.UDEMY_URL}courses/?search=${text}&price=price-free&is_affiliate_agreed=True&instructional_level=beginner"
        UdemyApi().getRecommendedCoursesWithQuery(query).enqueue(object : Callback<CoursesResponse> {
            override fun onResponse(
                call: Call<CoursesResponse>,
                response: Response<CoursesResponse>
            ) {
                if (response.isSuccessful && response.body()!=null) {
                    val adapter = CourseAdapter(response.body()!!.results as ArrayList<Course>,response.body()!!.results.size)
                    recyclerview.layoutManager = LinearLayoutManager(this@MoreJobsCoursesActivity)
                    recyclerview.visibility= View.VISIBLE
                    recyclerview.adapter = adapter

                }
            }

            override fun onFailure(call: Call<CoursesResponse>, t: Throwable) {
                Toast.makeText(this@MoreJobsCoursesActivity,t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


}