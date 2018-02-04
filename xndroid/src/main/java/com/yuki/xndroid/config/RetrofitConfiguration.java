package com.yuki.xndroid.config;

import android.content.Context;

import retrofit2.Retrofit;

/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/3
 * 邮箱：125508663@qq.com
 **/
public interface RetrofitConfiguration {
	void configRetrofit(Context context, Retrofit.Builder retrofitBuilder);
}
