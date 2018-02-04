package com.yuki.vision.module.home

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuki.vision.R
import com.yuki.vision.app.durationFormat
import com.yuki.vision.module.home.HomeResponse.Issue.Item
import com.yuki.vision.module.home.HomeResponse.Issue.Item.Companion.TYPE_TEXT_HEAD
import com.yuki.vision.module.home.HomeResponse.Issue.Item.Companion.TYPE_VIDEO
import com.yuki.xndroid.imageLoader.ImageLoader


/**
 * 项目：WeiShi
 * 作者：Yuki - 2018/1/24
 * 邮箱：125508663@qq.com
 **/

class HomeAdapter : BaseMultiItemQuickAdapter<Item, BaseViewHolder>(null) {
	init {
		addItemType(TYPE_VIDEO, R.layout.item_home_content)
		addItemType(TYPE_TEXT_HEAD, R.layout.item_home_header)
	}
	
	
	override fun convert(helper : BaseViewHolder, item : Item) {
		if (helper.itemViewType == TYPE_TEXT_HEAD)
			helper.setText(R.id.tvHeader, item.data?.text)
		else
			setVideoItem(helper, item)
		
	}
	
	/**
	 * 加载 content item
	 */
	private fun setVideoItem(helper : BaseViewHolder, item : Item) {
		val itemData = item.data
		
		val cover = itemData?.cover?.feed
		var avatar = itemData?.author?.icon
		var tagText : String? = ""
		
		// 作者出处为空，就显获取提供者的信息
		if (avatar.isNullOrEmpty()) {
			avatar = itemData?.provider?.icon
		}
		// 加载封页图
		ImageLoader.loadImage(helper.getView(R.id.iv_cover_feed), cover, R.mipmap.bg_loading,R.mipmap.bg_error)
		// 如果提供者信息为空，就显示默认
		ImageLoader.loadRoundImage(helper.getView(R.id.view_avatar), avatar, R.mipmap.default_avatar, R.mipmap.default_avatar)
		
		helper.setText(R.id.tv_title, itemData?.title ?: "")
		
		//遍历标签
		itemData?.tags?.take(4)?.forEach {
			tagText += (it.name + "/")
		}
		// 格式化时间
		val timeFormat = durationFormat(itemData?.duration)
		
		tagText += timeFormat
		
		helper.setText(R.id.tv_tag, tagText)
		
		helper.setText(R.id.tv_category,  itemData?.category)
		
	}
}