package com.yuki.vision.mvp.presenter

import com.yuki.vision.app.applySchedulers
import com.yuki.vision.mvp.model.HotModel
import com.yuki.vision.mvp.ui.fragment.HotFragment
import com.yuki.xndroid.base.mvp.XPresenter
import com.yuki.xndroid.di.scope.FragmentScope
import com.yuki.xndroid.kit.RxErrorListener
import javax.inject.Inject


@FragmentScope
class HotPresenter @Inject
constructor(private val rxErrorListener: RxErrorListener, model: HotModel, view: HotFragment) : XPresenter<HotModel, HotFragment>(model, view) {
    fun getTabInfo() {
        mModel.getTabInfo()
                .applySchedulers(mView)
                .subscribe({
                    mView.initData(it)
                }, {
                    mView.initDataFail(rxErrorListener.handleRxError(it))
                })
    }
}
