package com.yuki.xndroid.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuki.xndroid.base.mvp.IPresenter;
import com.yuki.xndroid.base.mvp.IView;
import com.yuki.xndroid.di.component.AppComponent;
import com.yuki.xndroid.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;


public abstract class XFragment<P extends IPresenter> extends RxSupportFragment implements IView {
	@Nullable
	@Inject
	protected P mPresenter;
	
	protected View mRootView;
	
	/**
	 * 提供AppComponent(提供所有的单例对象)给实现类，进行Component依赖
	 *
	 * @param appComponent
	 */
	public void initFragmentComponent(AppComponent appComponent) {
	
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mRootView = inflater.inflate(initLayoutId(), container, false);
		return mRootView;
	}
	
	protected abstract int initLayoutId();
	
	@Override
	public void onLazyInitView(@Nullable Bundle savedInstanceState) {
		super.onLazyInitView(savedInstanceState);
		initFragmentComponent(AppUtils.initAppComponent(getContext()));
		if (useEventBus())
			EventBus.getDefault().register(this);
		initView(savedInstanceState);
		initEvent();
	}
	
	protected void initEvent() {
	
	}
	
	public boolean useEventBus() {
		return false;
	}
	
	protected abstract void initView(Bundle savedInstanceState);
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mPresenter != null)
			mPresenter.onDestroy();//释放资源
		if (useEventBus())
			EventBus.getDefault().unregister(this);
		
	}
}
