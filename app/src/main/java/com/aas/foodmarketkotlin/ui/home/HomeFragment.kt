package com.aas.foodmarketkotlin.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aas.foodmarketkotlin.FoodMarket
import com.aas.foodmarketkotlin.R
import com.aas.foodmarketkotlin.model.response.home.Data
import com.aas.foodmarketkotlin.model.response.home.HomeResponse
import com.aas.foodmarketkotlin.model.response.login.User
import com.aas.foodmarketkotlin.ui.detail.DetailActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeAdapter.ItemAdapterCallback, HomeContract.View {

    private var newTasteList: ArrayList<Data> = ArrayList()
    private var popularList: ArrayList<Data> = ArrayList()
    private var recommendedList: ArrayList<Data> = ArrayList()

    private lateinit var presenter: HomePresenter
    var progressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        presenter = HomePresenter(this)
        presenter.getHome()

    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        var user = FoodMarket.getApp().getUser()
        var userResponse = Gson().fromJson(user, User::class.java)

        if (!userResponse.profile_photo_url.isNullOrEmpty()) {
            Glide.with(requireActivity())
                .load(userResponse.profile_photo_url)
                .into(ivProfile)
        }
    }

    override fun onClick(v: View, data: Data) {
        val detail = Intent(activity, DetailActivity::class.java).putExtra("data", data)
        startActivity(detail)
    }

    override fun onHomeSuccess(homeResponse: HomeResponse) {

        for (a in homeResponse.data.indices) {
            var items: List<String> = homeResponse.data[a].types?.split(",") ?: ArrayList()
            for (x in items.indices) {
                if (items[x].equals("new_food", true)) {
                    newTasteList.add(homeResponse.data[a])
                } else if (items[x].equals("recommended", true)) {
                    recommendedList.add(homeResponse.data[a])
                } else if (items[x].equals("popular", true)) {
                    popularList.add(homeResponse.data[a])
                }
            }
        }

        var adapter = HomeAdapter(homeResponse.data, this)
        var layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcList.layoutManager = layoutManager
        rcList.adapter = adapter

        val sectionPagerAdapter = SectionPagerAdapter(childFragmentManager)
        sectionPagerAdapter.setData(newTasteList, popularList, recommendedList)
        viewPager.adapter = sectionPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onHomeFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}