package com.aas.foodmarketkotlin.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.aas.foodmarketkotlin.R
import com.aas.foodmarketkotlin.model.response.home.Data
import com.aas.foodmarketkotlin.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    var bundle:Bundle ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as DetailActivity).toolbarDetail()

        arguments?.let {
            DetailFragmentArgs.fromBundle(it).data?.let {
                initView(it)
            }
        }

        btnOrderNow.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_payment, bundle)
        }
    }

    private fun initView(data: Data?) {

        bundle = bundleOf("data" to data)

        data?.let {
            Glide.with(requireContext())
                .load(it.picturePath)
                .into(ivPoster)

            tvTitle.text = it.name
            tvDesc.text = it.description
            tvIngredients.text = it.ingredients

            rbFood.rating = it.rate?.toFloat() ?: 0f
            tvRate.text = it.rate?.toFloat().toString()

            tvTotal.formatPrice(it.price.toString())
        }
    }
}