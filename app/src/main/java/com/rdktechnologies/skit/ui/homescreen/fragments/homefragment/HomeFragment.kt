package com.rdktechnologies.skit.ui.homescreen.fragments.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.helperclasses.apiclasses.EligibleJobResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.Jobs
import com.rdktechnologies.skit.utils.SharedPreference
import com.technicalrupu.sportsapp.HelperClasses.Api.MyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        return view
    }

    fun init(view: View) {
        recyclerview = view.findViewById(R.id.home_recyclerView)
        txtGm = view.findViewById<TextView>(R.id.txtGm)
        txtGm.text=setWishing()
        getAllEligibleJobs()
    }

    private fun getAllEligibleJobs(){
        MyApi().getAllEligibleJobs().enqueue(object : Callback<EligibleJobResponse> {
            override fun onResponse(
                call: Call<EligibleJobResponse>,
                response: Response<EligibleJobResponse>
            ) {
                if (response.isSuccessful && response.body()!=null) {
                    if(response.body()!!.error!=true) {

                        loadJobs(response.body()!!.data!! as ArrayList<Jobs>)
                    }else{
                        Toast.makeText(context,"Something went wrong...", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<EligibleJobResponse>, t: Throwable) {
                Toast.makeText(context,t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun loadJobs(list:ArrayList<Jobs>){
        val adapter = JobsAdapter(list)
        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.adapter = adapter
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