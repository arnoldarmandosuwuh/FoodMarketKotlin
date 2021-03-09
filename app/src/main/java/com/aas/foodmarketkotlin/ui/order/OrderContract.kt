package com.aas.foodmarketkotlin.ui.order

import com.aas.foodmarketkotlin.base.BasePresenter
import com.aas.foodmarketkotlin.base.BaseView
import com.aas.foodmarketkotlin.model.response.transaction.TransactionResponse

interface OrderContract {

    interface  View: BaseView {
        fun onTransactionSuccess(transactionResponse: TransactionResponse)
        fun onTransactionFailed(message: String)
    }

    interface Presenter : OrderContract, BasePresenter {
        fun getTransaction()
    }
}