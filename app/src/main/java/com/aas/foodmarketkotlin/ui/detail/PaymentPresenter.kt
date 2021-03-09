package com.aas.foodmarketkotlin.ui.detail

import android.view.View
import com.aas.foodmarketkotlin.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PaymentPresenter(private val view: PaymentContract.View) : PaymentContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getCheckout(
        foodId: String,
        userId: String,
        quantity: String,
        total: String,
        viewParms: View
    ) {
        view.showLoading()
        val disposable =
            HttpClient.getInstance().getApi()?.checkout(foodId, userId, quantity, total, "PENDING")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                    {
                        view.dismissLoading()
                        if (it.meta?.status.equals("success", true)) {
                            it.data?.let { it1 -> view.onCheckoutSuccess(it1, viewParms) }
                        } else {
                            it.meta?.let { it1 -> view.onCheckoutFailed(it1.message) }
                        }
                    }, {
                        view.dismissLoading()
                        view.onCheckoutFailed(it.message.toString())
                    }
                )
        disposable?.let { mCompositeDisposable?.add(it) }
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}