package com.aas.foodmarketkotlin.ui.auth.signin

import com.aas.foodmarketkotlin.base.BasePresenter
import com.aas.foodmarketkotlin.base.BaseView
import com.aas.foodmarketkotlin.model.response.login.LoginResponse

interface SigninContract {

    interface  View: BaseView {
        fun onLoginSuccess(loginResponse: LoginResponse)
        fun onLoginFailed(message: String)
    }

    interface Presenter : SigninContract, BasePresenter {
        fun submitLogin(email: String, password: String)
    }
}