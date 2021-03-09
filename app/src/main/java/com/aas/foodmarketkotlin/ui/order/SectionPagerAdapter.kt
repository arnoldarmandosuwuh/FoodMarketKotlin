package com.aas.foodmarketkotlin.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aas.foodmarketkotlin.model.response.transaction.Data
import com.aas.foodmarketkotlin.ui.order.inprogress.InprogressFragment
import com.aas.foodmarketkotlin.ui.order.pastorders.PastordersFragment

class SectionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var inprogressList:ArrayList<Data> ?= ArrayList()
    var pastordersList:ArrayList<Data> ?= ArrayList()

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "In Progress"
            1 -> "Past Orders"
            else -> ""

        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        val fragment : Fragment
        return when (position) {
            0 -> {
                fragment = InprogressFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", inprogressList)
                fragment.arguments = bundle
                return  fragment
            }
            1 -> {
                fragment = PastordersFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", pastordersList)
                fragment.arguments = bundle
                return  fragment
            }
            else -> {
                fragment = InprogressFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", inprogressList)
                fragment.arguments = bundle
                return  fragment
            }
        }
    }

    fun setData(inprogressListParms:ArrayList<Data>?, pastordersListParms:ArrayList<Data>?) {
        inprogressList = inprogressListParms
        pastordersList = pastordersListParms
    }
}