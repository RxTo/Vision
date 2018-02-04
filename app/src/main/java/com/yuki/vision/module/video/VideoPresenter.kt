package com.yuki.vision.module.video

import com.yuki.vision.app.applySchedulers
import com.yuki.vision.module.home.HomeResponse.Issue.Item
import com.yuki.vision.module.home.HomeResponse.Issue.Item.Companion.TEXT_CARD
import com.yuki.vision.module.home.HomeResponse.Issue.Item.Companion.TYPE_TEXT_HEAD
import com.yuki.xndroid.base.mvp.XPresenter
import com.yuki.xndroid.di.scope.ActivityScope
import com.yuki.xndroid.kit.RxErrorListener
import com.yuki.xndroid.utils.DeviceUtils
import javax.inject.Inject


@ActivityScope
class VideoPresenter @Inject
constructor(val rxErrorListener : RxErrorListener, model : VideoModel, view : VideoActivity) : XPresenter<VideoModel, VideoActivity>(model, view) {
	fun loadVideoInfo(itemData : Item?) {
		val playInfo = itemData?.data?.playInfo
		var url : String
		val isWifi = DeviceUtils.getNetworkType(mView) == DeviceUtils.NETTYPE_WIFI
		playInfo?.let {
			//有不同清晰度就根据网络情况选择,wifi选高清,移动选普通
			url = if (it.size > 1) it.filter { if (isWifi) it.type == "high" else it.type == "normal" }[0].url
			//否则选择默认
			else itemData.data.playUrl
			mView.initVideoUrl(url)
			requestRelatedVideo(itemData, true)
		}
	}
	
	
	
	/**
	 * 请求相关的视频数据
	 */
	private fun requestRelatedVideo(data : Item, isRefresh : Boolean) {
		data.itemType = TYPE_TEXT_HEAD
		mModel.requestRelatedData(data.data?.id ?: 0)
				.map {
					it.itemList.forEachIndexed { index, item ->
						when {
							item.type == "textCard" -> item.itemType = TEXT_CARD
							item.type == "videoSmallCard" -> item.itemType = Item.VIDEO_CARD
						}
					}
					return@map it
				}
				.applySchedulers(mView)
				.subscribe({
					it.itemList.add(0, data)
					mView.initData(it.itemList, isRefresh)
				}, {
					mView.initDataFail(rxErrorListener.handleRxError(it), isRefresh)
				})
	}
}
