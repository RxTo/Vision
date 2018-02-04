package com.yuki.vision.module.discovery.category

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuki.vision.R
import com.yuki.xndroid.imageLoader.ImageLoader

/**
 * Created by xuhao on 2017/11/29.
 * desc: 分类的 Adapter
 */

class CategoryAdapter : BaseQuickAdapter<CategoryResponse, BaseViewHolder>(R.layout.item_category) {
	override fun convert(helper : BaseViewHolder, item : CategoryResponse?) {
		ImageLoader.loadImage(helper.getView(R.id.iv_category), item?.bgPicture, R.color.light_transparent)
		helper.setText(R.id.tv_category_name,item?.name)
	}
	
	
}
