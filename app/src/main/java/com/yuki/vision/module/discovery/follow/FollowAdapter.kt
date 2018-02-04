package com.yuki.vision.module.discovery.follow

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuki.vision.R
import com.yuki.vision.app.goToVideoPlayer
import com.yuki.vision.module.home.HomeResponse.Issue.Item
import com.yuki.vision.widget.RecycleViewDivider
import com.yuki.xndroid.imageLoader.ImageLoader

/**
 * desc: 分类的 Adapter
 */

class FollowAdapter : BaseQuickAdapter<Item, BaseViewHolder>(R.layout.item_follow) {
	override fun convert(helper : BaseViewHolder, item : Item?) {
		when {
			item?.type == "videoCollectionWithBrief" -> setAuthorInfo(item, helper)
			else ->
				throw IllegalAccessException("Api 解析出错了，出现其他类型")
		}
		
	}
	
	/**
	 * 加载作者信息
	 */
	private fun setAuthorInfo(item : Item?, helper : BaseViewHolder) {
		val headerData = item?.data?.header
		/**
		 * 加载作者头像
		 */
		ImageLoader.loadRoundImage(helper.getView(R.id.iv_avatar), headerData?.icon, R.mipmap.default_avatar)
		helper.setText(R.id.tv_title, headerData?.title)
		helper.setText(R.id.tv_desc, headerData?.description)
		
		val recyclerView = helper.getView<RecyclerView>(R.id.fl_recyclerView)
		/**
		 * 设置嵌套水平的 RecyclerView
		 */
		recyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
		val horizontalAdapter = FollowHorizontalAdapter(item?.data?.itemList)
		recyclerView.adapter = horizontalAdapter
		horizontalAdapter.setOnItemClickListener({ adapter, view, position ->
			goToVideoPlayer(mContext as Activity, view, horizontalAdapter.getItem(position))
		})
		
	}
	
	
	
	
}
