package com.yuki.vision.module.home.search

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexboxLayoutManager
import com.yuki.vision.R

/**
 * 项目：Vision
 * 作者：Yuki - 2018/1/29
 * 邮箱：125508663@qq.com
 **/

class HotSearchAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_flow_text) {
	override fun convert(helper : BaseViewHolder, item : String?) {
		helper.setText(R.id.tv_title, item)
		val params = helper.getView<TextView>(R.id.tv_title).layoutParams
		if (params is FlexboxLayoutManager.LayoutParams) {
			params.flexGrow = 1.0f
		}
	}
}