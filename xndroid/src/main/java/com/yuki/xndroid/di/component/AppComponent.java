package com.yuki.xndroid.di.component;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.yuki.xndroid.base.delegate.AppDelegate;
import com.yuki.xndroid.di.module.AppModule;
import com.yuki.xndroid.di.module.ClientModule;
import com.yuki.xndroid.di.module.ConfigModule;
import com.yuki.xndroid.kit.RxErrorListener;
import com.yuki.xndroid.repository.IRepositoryManager;
import com.yuki.xndroid.utils.AppUtils;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * ================================================
 * 可通过 {@link AppUtils#initAppComponent(Context)} 拿到此接口的实现类
 * 拥有此接口的实现类即可调用对应的方法拿到 Dagger 提供的对应实例
 * ================================================
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, ConfigModule.class})
public interface AppComponent {
	Application application();
	
	//用于管理网络请求层,以及数据缓存层
	IRepositoryManager repositoryManager();
	
	//用与统一处理RxAndroid的错误
	RxErrorListener rxErrorListener();
	
	//OkHttpClient网络请求
	OkHttpClient okHttpClient();
	
	//gson
	Gson gson();
	
	
	void inject(AppDelegate delegate);
}
