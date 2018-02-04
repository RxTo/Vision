package com.yuki.vision.module.discovery.follow

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuki.vision.R
import com.yuki.vision.app.durationFormat
import com.yuki.vision.module.home.HomeResponse.Issue.Item
import com.yuki.xndroid.imageLoader.ImageLoader

/**
 * desc: 关注   水平的 RecyclerViewAdapter
 */
class FollowHorizontalAdapter(data : ArrayList<Item>?) : BaseQuickAdapter<Item, BaseViewHolder>(R.layout.item_follow_horizontal,data) {
	override fun convert(helper : BaseViewHolder, item : Item?) {
		val itemData = item?.data
		ImageLoader.loadImage(helper.getView(R.id.iv_cover_feed), itemData?.cover?.feed, R.mipmap.bg_loading,R.mipmap.bg_load_error)
		//横向 RecyclerView 封页图下面标题
		helper.setText(R.id.tv_title, itemData?.title ?: "")
		
		// 格式化时间
		val timeFormat = durationFormat(itemData?.duration)
		//标签
		helper.run {
			if (itemData?.tags != null && itemData.tags.size > 0) {
				setText(R.id.tv_tag, "${itemData.tags[0].name} / $timeFormat")
			} else {
				setText(R.id.tv_tag, timeFormat)
			}
		}
	}
	
	
}
