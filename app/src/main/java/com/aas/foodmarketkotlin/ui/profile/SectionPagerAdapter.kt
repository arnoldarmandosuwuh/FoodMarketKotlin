package com.aas.foodmarketkotlin.ui.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aas.foodmarketkotlin.ui.profile.account.ProfileAccountFragment
import com.aas.foodmarketkotlin.ui.profile.foodmarket.ProfileFoodmarketFragment

class SectionPagerAdapter(fm: FragmentManager?, private val number_tabs: Int) :
    FragmentPagerAdapter(
        fm!!
    ) {

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Account"
            1 -> "FoodMarket"
            else -> null

        }
    }

    override fun getCount(): Int {
        return number_tabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ProfileAccountFragment()
            1 -> ProfileFoodmarketFragment()
            else -> ProfileAccountFragment()
        }
    }
}