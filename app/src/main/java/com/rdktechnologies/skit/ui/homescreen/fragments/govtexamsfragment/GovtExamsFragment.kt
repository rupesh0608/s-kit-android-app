package com.rdktechnologies.skit.ui.homescreen.fragments.govtexamsfragment

import android.annotation.SuppressLint
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
import com.rdktechnologies.skit.helperclasses.apiclasses.EligibleJobResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.Jobs
import com.rdktechnologies.skit.helperclasses.apiclasses.Services
import com.rdktechnologies.skit.ui.homescreen.fragments.homefragment.CourseAdapter
import com.rdktechnologies.skit.ui.homescreen.fragments.homefragment.JobsAdapter
import com.rdktechnologies.skit.ui.profilescreen.ProfileButtonModel
import com.rdktechnologies.skit.utils.SharedPreference
import com.rdktechnologies.skit.utils.hideProgressAlert
import com.rdktechnologies.skit.utils.showProgressAlert
import com.technicalrupu.sportsapp.HelperClasses.Api.MyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GovtExamsFragment : Fragment() {

    lateinit var recyclerview: RecyclerView
    lateinit var heading1:TextView
    lateinit var edtSearch: EditText
    private var examList:ArrayList<ProfileButtonModel>?= arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_govt_examsragment, container, false)

        init(view)
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                filterExams()
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
    fun filterExams(){
        val text=edtSearch.text.toString()
        recyclerview.visibility=View.GONE
        if(text.isEmpty() ||text.isBlank() )
            loadRecyclerView(examList!!)
        val filteredExamList=examList!!.filter {
                it -> it.text.contains(text,true)
        }
        loadRecyclerView(filteredExamList as ArrayList<ProfileButtonModel>)
    }
    fun init(view: View) {
        recyclerview =view.findViewById(R.id.govtExamsRecyclerview)
        recyclerview.visibility=View.VISIBLE
        heading1=view.findViewById(R.id.heading1)
        edtSearch=view.findViewById(R.id.edtSearch)
        loadUpcomingExams()
    }

    private fun loadUpcomingExams(){
        activity?.showProgressAlert()
        MyApi().getAllEligibleJobs(SharedPreference(requireActivity()).getLoginResponse()!!.data!!.id!!).enqueue(object :
            Callback<EligibleJobResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<EligibleJobResponse>,
                response: Response<EligibleJobResponse>
            ) {
                if (response.isSuccessful && response.body()!=null) {
                    if(response.body()!!.error!=true) {
                        val data = ArrayList<ProfileButtonModel>()
                        for (exam in response.body()!!.data!!) {
                            data.add(
                                ProfileButtonModel(
                                    text = exam.boardName,
                                    icon = R.drawable.skit_jobs
                                )
                            )
                        }
                        examList=data
                        activity?.hideProgressAlert()
                        loadRecyclerView(data)

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

    fun loadRecyclerView(data:ArrayList<ProfileButtonModel>){
        activity?.showProgressAlert()
        heading1.text="Found(${data.size})"
        val adapter = GovtExamsAdapter(data)
        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.visibility=View.VISIBLE
        recyclerview.adapter = adapter
        activity?.hideProgressAlert()
    }

}