package com.yuki.xndroid.base;

import android.support.annotation.NonNull;

import com.yuki.xndroid.di.component.AppComponent;


/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/3
 * 邮箱：125508663@qq.com
 * ==========================================
 * $desc$
 * ==========================================
 */
public interface IApp {
	@NonNull
	AppComponent getAppComponent();
}
