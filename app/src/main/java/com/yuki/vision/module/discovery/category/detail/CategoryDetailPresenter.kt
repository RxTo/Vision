package com.yuki.vision.module.discovery.category.detail

import com.yuki.vision.app.applySchedulers
import com.yuki.xndroid.base.mvp.XPresenter
import com.yuki.xndroid.di.scope.ActivityScope
import com.yuki.xndroid.kit.RxErrorListener
import javax.inject.Inject


@ActivityScope
class CategoryDetailPresenter @Inject
constructor(private val rxErrorListener : RxErrorListener, model : CategoryDetailModel,
		view : CategoryDetailActivity) : XPresenter<CategoryDetailModel, CategoryDetailActivity>(model, view) {
	
	private var nextPageUrl : String? = null
	/**
	 * 获取分类详情的列表信息
	 */
	fun getCategoryDetailList(id : Long) {
		mModel.getCategoryDetailList(id)
				.applySchedulers(mView)
				.subscribe({
					nextPageUrl = it.nextPageUrl
					mView.initData(it.itemList, true)
				}, {
					mView.initDataFail(rxErrorListener.handleRxError(it), true)
				})
	}
	
	/**
	 * 加载更多数据
	 */
	fun loadMoreData() {
		nextPageUrl?.let {
			mModel.loadMoreData(it)
					.applySchedulers(mView)
					.subscribe({
						nextPageUrl = it.nextPageUrl
						mView.initData(it.itemList, false)
					}, {
						mView.initDataFail(rxErrorListener.handleRxError(it), false)
					})
		} ?: mView.initDataEnd()
	}
}
