package com.yuki.vision.module.discovery.category

import com.yuki.vision.app.applySchedulers
import com.yuki.xndroid.base.mvp.XPresenter
import com.yuki.xndroid.di.scope.FragmentScope
import com.yuki.xndroid.kit.RxErrorListener

import javax.inject.Inject


@FragmentScope
class CategoryPresenter @Inject
constructor(private val rxErrorListener : RxErrorListener, model : CategoryModel,
		view : CategoryFragment) : XPresenter<CategoryModel, CategoryFragment>(model, view) {
	fun initData() {
		mModel.initData()
				.applySchedulers(mView)
				.subscribe({
					mView.initData(it, false)
				}, {
					mView.initDataFail(rxErrorListener.handleRxError(it), false)
				})
	}
}
