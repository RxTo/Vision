package com.yuki.xndroid.config;

import android.content.Context;

import io.rx_cache2.internal.RxCache;

/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/18
 * 邮箱：125508663@qq.com
 **/


public interface RxCacheConfiguration {
	
	void configRxCache(Context context, RxCache.Builder rxCacheBuilder);
}
