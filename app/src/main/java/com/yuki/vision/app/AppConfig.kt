package com.yuki.vision.app

import android.app.Application
import android.content.Context
import android.support.v4.app.FragmentManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.yuki.vision.BuildConfig
import com.yuki.xndroid.base.delegate.ActivityLifecycleCallbacks
import com.yuki.xndroid.base.delegate.AppLifecycleCallbacks
import com.yuki.xndroid.config.AppConfiguration
import com.yuki.xndroid.repository.http.RequestInterceptor.Level.RESPONSE
import me.jessyan.retrofiturlmanager.RetrofitUrlManager


/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/6
 * 邮箱：125508663@qq.com
 */
class AppConfig : AppConfiguration {
	override fun applyOptions(context : Context, builder : com.yuki.xndroid.di.module.ConfigModule.Builder) {
		builder.baseUrl(AppApi.BASE_URL)
				.printHttpLogLevel(RESPONSE)
				.okHttpConfiguration({ _, okHttpBuilder ->
					RetrofitUrlManager.getInstance().with(okHttpBuilder)
				})
				.rxCacheConfiguration { _, rxCacheBuilder ->
					rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true)
				}
				.handleRxError(AppRxErrorListener(context))
	}
	
	override fun injectAppLifecycle(context : Context, AppLifecycles : MutableList<AppLifecycleCallbacks>) {
		AppLifecycles.add(object : AppLifecycleCallbacks() {
			override fun onCreate(application : Application) {
				val formatStrategy = PrettyFormatStrategy.newBuilder()
						.showThreadInfo(false) // 隐藏线程信息 默认：显示
						.methodCount(1)         // 决定打印多少行（每一行代表一个方法）默认：2
						.tag("Xmvp")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
						.build()
				Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
					override fun isLoggable(priority : Int, tag : String?) : Boolean {
						return BuildConfig.DEBUG
					}
				})
			}
		})
	}
	
	override fun injectActivityLifecycle(context : Context, activityLifecycles : MutableList<ActivityLifecycleCallbacks>) {
		activityLifecycles.add(object : ActivityLifecycleCallbacks() {
//			override fun onActivityCreated(activity : Activity, savedInstanceState : Bundle?) {
//				Logger.d("onActivityCreated: ${activity.componentName.className}")
//			}
//
//			override fun onActivityDestroyed(activity : Activity) {
//				Logger.d("onActivityDestroyed: ${activity.componentName.className}")
//			}
		})
	}
	
	override fun injectFragmentLifecycle(context : Context, fragmentLifecycles : MutableList<FragmentManager.FragmentLifecycleCallbacks>) {
	
	}
}


