package com.rdktechnologies.skit.ui.homescreen.fragments.servicefragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdktechnologies.skit.R
import com.rdktechnologies.skit.helperclasses.apiclasses.ServiceResponse
import com.rdktechnologies.skit.helperclasses.apiclasses.Services
import com.rdktechnologies.skit.utils.hideProgressAlert
import com.rdktechnologies.skit.utils.showProgressAlert
import com.technicalrupu.sportsapp.HelperClasses.Api.MyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ServicesFragment : Fragment() {

    lateinit var recyclerview: RecyclerView
    lateinit var edtSearch: EditText
    private var serviceList: ArrayList<Services>? = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_services, container, false)
        init(view)
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                filterService()
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

    fun filterService() {
        val text = edtSearch.text.toString()
        if (text.isEmpty() || text.isBlank())
            loadServices(serviceList!!)
        val filteredServiceList = serviceList!!.filter { it ->
            it.name.contains(text, true)
        }
        loadServices(filteredServiceList as ArrayList<Services>)
    }

    fun init(view: View) {
        recyclerview = view.findViewById(R.id.serviceRecyclerView)
        edtSearch = view.findViewById(R.id.edtSearch)
        getAllServices()
    }

    private fun getAllServices() {
        activity?.showProgressAlert()
        MyApi().getAllServices().enqueue(object : Callback<ServiceResponse> {
            override fun onResponse(
                call: Call<ServiceResponse>,
                response: Response<ServiceResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.error != true) {
                        val data = ArrayList<Services>()
                        for (module in response.body()!!.data!!) {
                            if (module.isActive == false)
                                continue
                            for (service in module.services!!) {
                                if (service.isActive == true) {
                                    data.add(service)
                                }
                            }
                        }
                        serviceList = data
                        activity?.hideProgressAlert()
                        loadServices(data)
                    } else {
                        activity?.hideProgressAlert()
                        Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<ServiceResponse>, t: Throwable) {
                activity?.hideProgressAlert()
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun loadServices(list: ArrayList<Services>) {
        activity?.showProgressAlert()
        val adapter = ServiceAdapter(list)
        recyclerview.layoutManager = GridLayoutManager(activity, 3)
        recyclerview.adapter = adapter
        activity?.hideProgressAlert()
    }


}