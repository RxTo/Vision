package com.yuki.xndroid.imageLoader;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;

/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/7
 * 邮箱：125508663@qq.com
 **/


public class ImageLoader {
	public static void loadImage(@NonNull ImageView imageView, @Nullable String url) {
		loadImage(imageView, url, 0, 0);
	}
	
	public static void loadImage(@NonNull ImageView imageView, @Nullable String url, int placeholderRes) {
		loadImage(imageView, url, placeholderRes, 0);
	}
	
	public static void loadImage(@NonNull ImageView imageView, @Nullable String url, int placeholderRes, int errorRes) {
		loadImage(imageView, url, placeholderRes, errorRes, null);
	}
	
	public static void loadImage(ImageView imageView, String url, int placeholderRes, int errorRes, RequestListener<Drawable> callback) {
		GlideApp.with(imageView)
				.load(url)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(placeholderRes)
				.error(errorRes)
				.centerCrop()
				.listener(callback)
				.into(imageView);
		
	}
	
	public static void loadRoundImage(@NonNull ImageView imageView, @Nullable String url, int placeholderRes) {
		loadRoundImage(imageView,url,placeholderRes,0);
	}

	public static void loadRoundImage(@NonNull ImageView imageView, @Nullable String url, int placeholderRes, int errorRes) {
		GlideApp.with(imageView)
				.load(url)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(placeholderRes)
				.error(errorRes)
				.circleCrop()
				.transition(DrawableTransitionOptions.withCrossFade())
				.into(imageView);
	}
	
	
}
