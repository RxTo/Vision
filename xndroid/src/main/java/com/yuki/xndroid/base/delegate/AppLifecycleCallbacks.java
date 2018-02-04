package com.yuki.xndroid.base.delegate;

import android.app.Application;
import android.content.Context;

import com.yuki.xndroid.base.XApplication;


/**
 * ================================================
 * * 用于代理 {@link XApplication} 的生命周期
 * ===============================================
 */
public abstract class AppLifecycleCallbacks {
	
	public void attachBaseContext(Context base) {
	}
	
	public void onCreate(Application application) {
	}
	
	public void onTerminate(Application application) {
	}
	
	
}
