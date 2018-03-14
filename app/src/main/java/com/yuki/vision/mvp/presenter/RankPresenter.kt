package com.yuki.vision.mvp.presenter

import com.yuki.vision.app.applySchedulers
import com.yuki.vision.mvp.model.RankModel
import com.yuki.vision.mvp.ui.fragment.RankFragment
import com.yuki.xndroid.base.mvp.XPresenter
import com.yuki.xndroid.di.scope.FragmentScope
import com.yuki.xndroid.kit.RxErrorListener

import javax.inject.Inject


@FragmentScope
class RankPresenter @Inject
constructor(private val rxErrorListener: RxErrorListener, model: RankModel, view: RankFragment) : XPresenter<RankModel, RankFragment>(model, view) {
    fun requestRankList(url: String, isRefresh: Boolean) {
        mModel.requestRankList(url)
                .applySchedulers(mView)
                .subscribe({
                    mView.initData(it.itemList, isRefresh)
                }, {
                    mView.initDataFail(rxErrorListener.handleRxError(it), isRefresh)
                })
    }
}
