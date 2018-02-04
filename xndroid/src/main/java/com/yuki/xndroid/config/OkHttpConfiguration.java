package com.yuki.xndroid.config;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/3
 * 邮箱：125508663@qq.com
 **/

public interface OkHttpConfiguration {
	void configOkHttp(Context context, OkHttpClient.Builder okHttpBuilder);
}