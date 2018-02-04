package com.yuki.vision.module.home

import com.orhanobut.logger.Logger
import com.yuki.vision.app.applySchedulers
import com.yuki.vision.module.home.HomeResponse.Issue.Item
import com.yuki.xndroid.base.mvp.XPresenter
import com.yuki.xndroid.kit.RxErrorListener
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class HomePresenter @Inject constructor(private val rxErrorListener : RxErrorListener, model : HomeModel,
		view : HomeFragment) : XPresenter<HomeModel, HomeFragment>(model, view) {
	
	private var nextPageUrl : String? = null
	
	lateinit var bannerList : MutableList<Item>
	
	
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
	
	
	private fun initObservable(observable : Observable<HomeResponse>, isRefresh : Boolean) {
		observable.subscribeOn(Schedulers.io())
				.flatMap {
					//如果是刷新保存top的广告栏再加载一页数据
					if (isRefresh) {
						bannerList = it.issueList[0].itemList
						bannerList.filter {
							it.type == "banner2" || it.type == "horizontalScrollCard"
						}.forEach {
									bannerList.remove(it)
								}
						mModel.initData(it.nextPageUrl)
					} else
						Observable.just(it)
				}
				.map {
					//多布局对Entity进行分类
					it.issueList[0].itemList.forEach {
						if (it.type == "textHeader") it.itemType = Item.TYPE_TEXT_HEAD
					}
					return@map it
				}
				.applySchedulers(mView)
				.doFinally {
					Logger.d("doFinally")
					mView.hideSkeleton()
				}
				.subscribe(
						{
							nextPageUrl = it.nextPageUrl
							mView.initData(it.issueList[0].itemList, isRefresh)
							//如果是刷新就更新头部广告栏
							if (isRefresh) initBanner(bannerList)
						},
						{
							mView.initDataFail(rxErrorListener.handleRxError(it), isRefresh)
						})
	}
	
	/**
	 * 分离广告标题和Url
	 * */
	private fun initBanner(data : List<Item>) {
		val bannerFeedList = ArrayList<String>()
		val bannerTitleList = ArrayList<String>()
		//取出banner 显示的 img 和 Title
		Observable.fromIterable(data).subscribe({ list ->
			bannerFeedList.add(list.data?.cover?.feed ?: "")
			bannerTitleList.add(list.data?.title ?: "")
		})
		mView.initBanner(bannerFeedList, bannerTitleList)
	}
}
