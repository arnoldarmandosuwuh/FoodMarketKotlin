package com.aas.foodmarketkotlin.ui.home

import com.aas.foodmarketkotlin.base.BasePresenter
import com.aas.foodmarketkotlin.base.BaseView
import com.aas.foodmarketkotlin.model.response.home.HomeResponse
import com.aas.foodmarketkotlin.model.response.login.LoginResponse

interface HomeContract {

    interface  View: BaseView {
        fun onHomeSuccess(homeResponse: HomeResponse)
        fun onHomeFailed(message: String)
    }

    interface Presenter : HomeContract, BasePresenter {
        fun getHome()
    }
}