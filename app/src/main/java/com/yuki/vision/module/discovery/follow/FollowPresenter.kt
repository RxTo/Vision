package com.yuki.vision.module.discovery.follow

import com.yuki.vision.app.applySchedulers
import com.yuki.vision.module.home.HomeResponse.Issue
import com.yuki.xndroid.base.mvp.XPresenter
import com.yuki.xndroid.di.scope.FragmentScope
import com.yuki.xndroid.kit.RxErrorListener
import io.reactivex.Observable
import javax.inject.Inject


@FragmentScope
class FollowPresenter @Inject
constructor(private val rxErrorListener : RxErrorListener, model : FollowModel,
		view : FollowFragment) : XPresenter<FollowModel, FollowFragment>(model, view) {
	
	private var nextPageUrl : String? = null
	
	/**
	 *  请求关注数据
	 */
	fun initData(isRefresh : Boolean) {
		when {
			isRefresh -> {
				initObservable(mModel.initData(), isRefresh)
			}
			else ->
				nextPageUrl?.let {
					initObservable(mModel.initData(it), isRefresh)
				} ?: mView.initDataEnd()
		}
	}
	
	private fun initObservable(observable : Observable<Issue>, isRefresh : Boolean) {
		observable.applySchedulers(mView)
				.subscribe(
						{
							nextPageUrl = it.nextPageUrl
							mView.initData(it.itemList, isRefresh)
						},
						{
							mView.initDataFail(rxErrorListener.handleRxError(it), isRefresh)
						})
	}
	
	
}
