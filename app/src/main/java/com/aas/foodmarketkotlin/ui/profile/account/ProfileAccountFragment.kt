package com.aas.foodmarketkotlin.ui.profile.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aas.foodmarketkotlin.R
import com.aas.foodmarketkotlin.ui.profile.ProfileMenuAdapter
import com.aas.foodmarketkotlin.ui.profile.ProfileMenuModel
import kotlinx.android.synthetic.main.fragment_profile_account.*

class ProfileAccountFragment : Fragment(), ProfileMenuAdapter.ItemAdapterCallback {

    private var adapter: ProfileMenuAdapter?=null
    private var menuArrayList: ArrayList<ProfileMenuModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addData()

        adapter = ProfileMenuAdapter(menuArrayList, this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        rcList.layoutManager = layoutManager
        rcList.adapter = adapter
    }

    fun addData() {
    menuArrayList = ArrayList()
        menuArrayList.add(ProfileMenuModel("Edit Profile"))
        menuArrayList.add(ProfileMenuModel("Home Address"))
        menuArrayList.add(ProfileMenuModel("Security"))
        menuArrayList.add(ProfileMenuModel("Payments"))
        menuArrayList.add(ProfileMenuModel("Sign Out"))
    }

    override fun onClick(v: View, data: ProfileMenuModel) {
        Toast.makeText(context, "ini menu yang kamu kklik "+ data.title, Toast.LENGTH_SHORT).show()
    }
}