package com.aas.foodmarketkotlin.ui.order.detailsorders

import com.aas.foodmarketkotlin.base.BasePresenter
import com.aas.foodmarketkotlin.base.BaseView

interface OrdersDetailContract {
    interface View : BaseView {
        fun onUpdateTransactionSuccess(message: String)
        fun onUpdateTransactionFailed(message: String)
    }

    interface Presenter : OrdersDetailContract, BasePresenter {
        fun getUpdateTransaction(id:String, status:String)
    }
}