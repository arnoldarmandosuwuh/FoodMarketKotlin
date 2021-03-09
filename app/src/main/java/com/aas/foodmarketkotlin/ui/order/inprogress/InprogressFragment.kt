package com.aas.foodmarketkotlin.ui.order.inprogress

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aas.foodmarketkotlin.R
import com.aas.foodmarketkotlin.model.response.transaction.Data
import com.aas.foodmarketkotlin.ui.order.detailsorders.OrdersDetailActivity
import kotlinx.android.synthetic.main.fragment_inprogress.*

class InprogressFragment : Fragment(), InprogressAdapter.ItemAdapterCallback {

    private var adapter: InprogressAdapter? = null
    var inprogressList: ArrayList<Data>? = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inprogress, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        inprogressList = arguments?.getParcelableArrayList("data")

        if (!inprogressList.isNullOrEmpty()) {
            adapter = InprogressAdapter(inprogressList!!, this)
            val layoutManager = LinearLayoutManager(activity)
            rcList.layoutManager = layoutManager
            rcList.adapter = adapter
        }
    }

    override fun onClick(v: View, data: Data) {
        val detail = Intent(activity, OrdersDetailActivity::class.java).putExtra("data", data)
        startActivity(detail)
    }


}