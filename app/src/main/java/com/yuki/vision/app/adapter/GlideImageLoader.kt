package com.yuki.vision.app.adapter

import android.content.Context
import android.widget.ImageView
import com.youth.banner.loader.ImageLoader
import com.yuki.vision.R
import com.yuki.xndroid.imageLoader.ImageLoader.loadImage

class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        if (imageView != null) {
            loadImage(imageView, path as String?, R.mipmap.bg_loading, R.mipmap.bg_error)
        }
    }
}
