package com.yuki.xndroid.base.delegate;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;

import com.yuki.xndroid.config.AppConfiguration;
import com.yuki.xndroid.config.ManifestParser;
import com.yuki.xndroid.di.component.AppComponent;
import com.yuki.xndroid.di.component.DaggerAppComponent;
import com.yuki.xndroid.di.module.AppModule;
import com.yuki.xndroid.di.module.ClientModule;
import com.yuki.xndroid.di.module.ConfigModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/2
 * 邮箱：125508663@qq.com
 * ================================================================================
 * AppDelegate 可以代理 Application 的生命周期,在对应的生命周期,执行对应的逻辑,因为 Java 只能单继承
 * 所以当遇到某些三方库需要继承于它的 Application 的时候,就只有自定义 Application 并继承于三方库的 Application
 * 这时就不用再继承 XApplication,只用在自定义Application中对应的生命周期调用AppDelegate对应的方法
 * 同时实现返回AppComponent的方法,框架就能照常运行
 * ================================================================================
 **/
public class AppDelegate extends AppLifecycleCallbacks {
	private Application mApplication;
	private AppComponent mAppComponent;
	protected List<AppConfiguration> mModules;
	private List<AppLifecycleCallbacks> mAppLifecycles;
	private List<ActivityLifecycleCallbacks> mActivityLifecycles;
	private ComponentCallbacks2 mComponentCallback;
	@Inject
	protected ActivityDelegate mActivityDelegate;
	
	public AppDelegate(Context context) {
		mModules = new ManifestParser(context).parse();
		mAppLifecycles = new ArrayList<>();
		mActivityLifecycles = new ArrayList<>();
		for (AppConfiguration module : mModules) {
			module.injectAppLifecycle(context, mAppLifecycles);
			module.injectActivityLifecycle(context, mActivityLifecycles);
		}
	}
	
	@NonNull
	public AppComponent getAppComponent() {
		return mAppComponent;
	}
	
	@Override
	public void attachBaseContext(Context base) {
		for (AppLifecycleCallbacks lifecycle : mAppLifecycles) {
			lifecycle.attachBaseContext(base);
		}
	}
	
	@Override
	public void onCreate(Application application) {
		this.mApplication = application;
		mAppComponent = DaggerAppComponent
				.builder()
				.appModule(new AppModule(application, mModules))
				.clientModule(new ClientModule())//用于提供okHttp和retrofit的单例
				.configModule(getGlobalConfigModule(mApplication, mModules))//全局配置
				.build();
		mAppComponent.inject(this);
		
		mApplication.registerActivityLifecycleCallbacks(mActivityDelegate);
		//通过ConfigModule往Activity生命周期注入相关方法
		for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
			mApplication.registerActivityLifecycleCallbacks(lifecycle);
		}
		for (AppLifecycleCallbacks lifecycle : mAppLifecycles) {
			lifecycle.onCreate(mApplication);
		}
		mComponentCallback = new AppComponentCallbacks(mApplication, mAppComponent);
		
		mApplication.registerComponentCallbacks(mComponentCallback);
	}
	
	@Override
	public void onTerminate(Application application) {
		if (mActivityDelegate != null)
			mApplication.unregisterActivityLifecycleCallbacks(mActivityDelegate);
		if (mComponentCallback != null) {
			mApplication.unregisterComponentCallbacks(mComponentCallback);
		}
		if (mActivityLifecycles != null && mActivityLifecycles.size() > 0) {
			for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
				mApplication.unregisterActivityLifecycleCallbacks(lifecycle);
			}
		}
		if (mAppLifecycles != null && mAppLifecycles.size() > 0) {
			for (AppLifecycleCallbacks lifecycle : mAppLifecycles) {
				lifecycle.onTerminate(mApplication);
			}
		}
		this.mAppComponent = null;
		this.mActivityLifecycles = null;
		this.mComponentCallback = null;
		this.mAppLifecycles = null;
		this.mApplication = null;
	}
	
	/**
	 * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
	 * 需要在AndroidManifest中声明{@link AppConfiguration}的实现类,和Glide的配置方式相似
	 */
	private ConfigModule getGlobalConfigModule(Context context, List<AppConfiguration> modules) {
		
		ConfigModule.Builder builder = ConfigModule
				.builder();
		
		for (AppConfiguration module : modules) {
			module.applyOptions(context, builder);
		}
		
		return builder.build();
	}
	
	private static class AppComponentCallbacks implements ComponentCallbacks2 {
		private Application mApplication;
		private AppComponent mAppComponent;
		
		public AppComponentCallbacks(Application application, AppComponent appComponent) {
			this.mApplication = application;
			this.mAppComponent = appComponent;
		}
		
		@Override
		public void onTrimMemory(int level) {
		
		}
		
		@Override
		public void onConfigurationChanged(Configuration newConfig) {
		
		}
		
		@Override
		public void onLowMemory() {
			//内存不足时清理不必要的资源
		}
	}
	
	
}
