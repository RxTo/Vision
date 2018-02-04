package com.yuki.vision.module.home

import android.content.Context
import android.widget.ImageView
import com.youth.banner.loader.ImageLoader
import com.yuki.vision.R
import com.yuki.xndroid.imageLoader.ImageLoader.loadImage

/**
 * 项目：WeiShi
 * 作者：Yuki - 2018/1/25
 * 邮箱：125508663@qq.com
 **/

class GlideImageLoader : ImageLoader() {
	override fun displayImage(context : Context?, path : Any?, imageView : ImageView?) {
		if (imageView != null) {
			loadImage(imageView, path as String?, R.mipmap.bg_loading, R.mipmap.bg_error)
		}
	}
}