package com.aas.foodmarketkotlin.ui.detail

import android.view.View
import com.aas.foodmarketkotlin.base.BasePresenter
import com.aas.foodmarketkotlin.base.BaseView
import com.aas.foodmarketkotlin.model.response.checkout.CheckoutResponse
import com.aas.foodmarketkotlin.model.response.home.HomeResponse
import com.aas.foodmarketkotlin.model.response.login.LoginResponse

interface PaymentContract {

    interface  View: BaseView {
        fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view: android.view.View)
        fun onCheckoutFailed(message: String)
    }

    interface Presenter : PaymentContract, BasePresenter {
        fun getCheckout(foodId: String, userId:String, quantity:String, total: String,view: android.view.View)
    }
}