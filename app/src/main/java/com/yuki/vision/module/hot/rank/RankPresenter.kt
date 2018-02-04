package com.yuki.vision.module.hot.rank

import com.yuki.vision.app.applySchedulers
import com.yuki.xndroid.base.mvp.XPresenter
import com.yuki.xndroid.di.scope.FragmentScope
import com.yuki.xndroid.kit.RxErrorListener

import javax.inject.Inject


@FragmentScope
class RankPresenter @Inject
constructor(private val rxErrorListener : RxErrorListener, model : RankModel, view : RankFragment) : XPresenter<RankModel, RankFragment>(model, view) {
	fun requestRankList(url : String,isRefresh:Boolean) {
		mModel.requestRankList(url)
				.applySchedulers(mView)
				.subscribe({
					mView.initData(it.itemList, isRefresh)
				}, {
					mView.initDataFail(rxErrorListener.handleRxError(it), isRefresh)
				})
	}
}
