package com.aas.foodmarketkotlin.ui.detail

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.aas.foodmarketkotlin.FoodMarket
import com.aas.foodmarketkotlin.R
import com.aas.foodmarketkotlin.model.response.checkout.CheckoutResponse
import com.aas.foodmarketkotlin.model.response.home.Data
import com.aas.foodmarketkotlin.model.response.login.User
import com.aas.foodmarketkotlin.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_payment.*


class PaymentFragment : Fragment(), PaymentContract.View {

    var total:Int = 0
    var progressDialog: Dialog ?= null
    lateinit var presenter: PaymentPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as DetailActivity).toolbarPayment()

        var data = arguments?.getParcelable<Data>("data")
        initView(data)
        initView()
        presenter = PaymentPresenter(this)

    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    private fun initView(data: Data?) {
        tvTitle.text = data?.name
        tvPrice.formatPrice(data?.price.toString())

        Glide.with(requireContext())
            .load(data?.picturePath)
            .into(ivPoster)

        tvNameItem.text = data?.name
        tvHarga.formatPrice(data?.price.toString())

        if (!data?.price.toString().isNullOrEmpty()) {
            var totalTax = data?.price?.div(10)
            tvTax.formatPrice(totalTax.toString())

            total = data?.price!! + totalTax!! + 50000
            tvTotal.formatPrice(total.toString())
        } else {
            tvPrice.text = "IDR 0"
            tvHarga.text = "IDR 0"
            tvTax.text = "IDR 0"
            tvTotal.text = "IDR 0"
            total = 0
        }

        var user = FoodMarket.getApp().getUser()
        var userResponse = Gson().fromJson(user, User::class.java)

        tvName.text = userResponse?.name
        tvPhoneNo.text = userResponse?.phoneNumber
        tvAddress.text = userResponse?.address
        tvCity.text = userResponse?.city
        tvHouse.text = userResponse?.houseNumber

        btnCheckout.setOnClickListener {
            presenter.getCheckout(
                data?.id.toString(),
                userResponse?.id.toString(),
                "1",
                total.toString(),
                it
            )
        }
    }

    override fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view: View) {

        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(checkoutResponse.paymentUrl)
        startActivity(i)

        Navigation.findNavController(view).navigate(R.id.action_payment_success)
    }

    override fun onCheckoutFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

}