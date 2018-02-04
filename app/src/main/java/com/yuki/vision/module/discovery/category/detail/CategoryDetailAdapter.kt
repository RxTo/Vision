package com.yuki.vision.module.discovery.category.detail

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuki.vision.R
import com.yuki.vision.app.durationFormat
import com.yuki.vision.module.home.HomeResponse.Issue.Item
import com.yuki.xndroid.imageLoader.ImageLoader

/**
 * Created by xuhao on 2017/11/30.
 * desc:分类详情Adapter
 */
class CategoryDetailAdapter : BaseQuickAdapter<Item, BaseViewHolder>(R.layout.item_category_detail) {
	override fun convert(helper : BaseViewHolder, item : Item?) {
		val itemData = item?.data
		val cover = itemData?.cover?.feed ?: ""
		// 加载封页图
		ImageLoader.loadImage(helper.getView(R.id.iv_image), cover, R.mipmap.placeholder_banner)
		helper.setText(R.id.tv_title, itemData?.title ?: "")
		
		// 格式化时间
		val timeFormat = durationFormat(itemData?.duration)
		
		helper.setText(R.id.tv_tag, "${itemData?.category}/$timeFormat")
	}
	
}

