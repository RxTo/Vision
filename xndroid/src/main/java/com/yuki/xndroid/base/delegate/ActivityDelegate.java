package com.yuki.xndroid.base.delegate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.yuki.xndroid.base.XActivity;
import com.yuki.xndroid.config.AppConfiguration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * ================================================
 *  框架对XActivity注入方法
 * ================================================
 */
public class ActivityDelegate extends ActivityLifecycleCallbacks {
	
	private Application mApplication;
	private List<AppConfiguration> mConfigModules;
	@Inject
	public ActivityDelegate(List<AppConfiguration> configModules, Application application) {
		this.mConfigModules = configModules;
		this.mApplication = application;
	}
	
	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		if (activity instanceof XActivity && ((XActivity) activity).injectFragmentLifecycle()) {
			registerFragmentCallbacks((FragmentActivity) activity);
		}
		if (activity instanceof XActivity && ((XActivity) activity).useEventBus())
			EventBus.getDefault().register(activity);
		
	}
	
	
	@Override
	public void onActivityDestroyed(Activity activity) {
		if (activity instanceof XActivity && ((XActivity) activity).useEventBus())
			EventBus.getDefault().unregister(activity);
	}
	
	private void registerFragmentCallbacks(FragmentActivity activity) {
		//通过外部往fragment生命周期注入
		List<FragmentManager.FragmentLifecycleCallbacks> fragmentLifecycles = new ArrayList<>();
		for (AppConfiguration module : mConfigModules) {
			module.injectFragmentLifecycle(mApplication, fragmentLifecycles);
		}
		for (FragmentManager.FragmentLifecycleCallbacks fragmentLifecycle : fragmentLifecycles) {
			activity.getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
		}
	}
	
}
