package com.aas.foodmarketkotlin.ui.profile

import com.aas.foodmarketkotlin.network.HttpClient
import com.aas.foodmarketkotlin.utils.Helpers.getErrorBodyMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfilePresenter (private val view:ProfileContract.View) : ProfileContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getProfile() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.profile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success")) {
                        it.data?.let { data -> view.onProfileSuccess(data) }
                    } else {
                        view.onProfileFailed(it.meta?.message.toString())
                    }

                },
                {
                    view.dismissLoading()
                    view.onProfileFailed(it.getErrorBodyMessage())
                })
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {}

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}