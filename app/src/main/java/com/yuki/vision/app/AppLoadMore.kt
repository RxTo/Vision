package com.yuki.vision.app

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.yuki.vision.R

/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/13
 * 邮箱：125508663@qq.com
 */


class AppLoadMore : LoadMoreView() {
	override fun getLayoutId() : Int {
		return R.layout.view_load_more
	}
	
	override fun getLoadingViewId() : Int {
		return R.id.load_more_loading_view
	}
	
	override fun getLoadFailViewId() : Int {
		return R.id.load_more_load_fail_view
	}
	
	override fun getLoadEndViewId() : Int {
		return R.id.load_more_load_end_view
	}
}
