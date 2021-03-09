package com.aas.foodmarketkotlin.ui.order.detailsorders

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aas.foodmarketkotlin.R
import com.aas.foodmarketkotlin.model.response.transaction.Data
import com.aas.foodmarketkotlin.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_orders_detail.*

class OrdersDetailFragment : Fragment(), OrdersDetailContract.View {

    var progressDialog: Dialog? = null
    lateinit var presenter : OrdersDetailPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            OrdersDetailFragmentArgs.fromBundle(it).data.let{
                initView(it)
            }
        }

        (activity as OrdersDetailActivity).toolbarPayment()
        initView()
        presenter = OrdersDetailPresenter(this)
    }

    private fun initView(data: Data?) {

        data?.let {
            tvTitle.text = it.food.name
            tvPrice.formatPrice(it.food.price.toString())
            Glide.with(requireContext())
                .load(it.food.picturePath)
                .into(ivPoster)

            tvNameItem.text = it.food.name
            tvHarga.formatPrice(it.food.price.toString())

            if (!it.food.price.toString().isNullOrEmpty()) {
                var totalTax = it.food.price.div(10)
                tvTax.formatPrice(totalTax.toString())

                var total = it.food.price + totalTax + 50000
                tvTotal.formatPrice(total.toString())
            } else {
                tvPrice.text = "IDR. 0"
                tvTax.text = "IDR. 0"
                tvTotal.text = "IDR. 0"
            }

            tvNama.text = it.user.name
            tvPhone.text = it.user.phoneNumber
            tvAddress.text = it.user.address
            tvHouseNo.text = it.user.houseNumber
            tvCity.text = it.user.city

            tvOrderStatus.text = it.id.toString()

            if (it.status.equals("ON_DELIVERY", true)) {
                btnCancelled.visibility = View.VISIBLE
                constraintLayout3.visibility = View.VISIBLE
                tvPending.text = "Paid"
            } else if (it.status.equals("SUCCESS", true)) {
                btnCancelled.visibility = View.INVISIBLE
                constraintLayout3.visibility = View.VISIBLE
                tvPending.text = "Paid"
            } else if (it.status.equals("PENDING", true)) {
                btnCancelled.visibility = View.VISIBLE
                btnCancelled.text = "Pay Now"
                constraintLayout3.visibility = View.VISIBLE
                tvPending.text = "Pending"
            }

            btnCancelled.setOnClickListener {it2 ->
                if (btnCancelled.text.equals("Pay Now")) {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(it.paymentUrl)
                    startActivity(i)
                } else {
                    presenter.getUpdateTransaction(it.id.toString(), "CANCELLED")
                }
            }
        }
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

    override fun onUpdateTransactionSuccess(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        requireActivity().finish()
    }

    override fun onUpdateTransactionFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }


}