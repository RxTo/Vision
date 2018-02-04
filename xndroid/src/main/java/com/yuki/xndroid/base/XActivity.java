package com.yuki.xndroid.base;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yuki.xndroid.base.mvp.IPresenter;
import com.yuki.xndroid.base.mvp.IView;
import com.yuki.xndroid.di.component.AppComponent;
import com.yuki.xndroid.utils.AppUtils;

import javax.inject.Inject;


/**
 * ================================================================================
 * 一个拥有DataBinding框架的基Activity
 * 这里根据项目业务可以换成你自己熟悉的BaseActivity
 * =================================================================================
 */

public abstract class XActivity<P extends IPresenter> extends RxSupportActivity implements IView {
	@Nullable
	@Inject
	protected P mPresenter;
	
	/**
	 * 提供AppComponent(提供所有的单例对象)给实现类，进行Component依赖
	 *
	 * @param appComponent
	 */
	public void initActivityComponent(AppComponent appComponent) {
	
	}
	
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(initLayoutId());
		initActivityComponent(AppUtils.initAppComponent(this));
		initView(savedInstanceState);
		initEvent();
	}
	
	protected abstract void initView(@Nullable Bundle savedInstanceState);
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPresenter != null)
			mPresenter.onDestroy();//释放资源
		
	}
	
	protected void initEvent() {
	
	}
	
	public boolean injectFragmentLifecycle() {
		return true;
	}
	
	
	public boolean useEventBus() {
		return false;
	}
	
	
	protected abstract int initLayoutId();
	
	
}
