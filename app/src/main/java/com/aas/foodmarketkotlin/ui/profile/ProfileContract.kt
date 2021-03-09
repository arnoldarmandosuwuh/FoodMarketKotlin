package com.aas.foodmarketkotlin.ui.profile

import com.aas.foodmarketkotlin.base.BasePresenter
import com.aas.foodmarketkotlin.base.BaseView
import com.aas.foodmarketkotlin.model.response.ProfileResponse

interface ProfileContract {
    interface View : BaseView {
        fun onProfileSuccess(profileResponse: ProfileResponse)
        fun onProfileFailed(message: String)
    }

    interface Presenter : ProfileContract, BasePresenter {
        fun getProfile()
    }
}