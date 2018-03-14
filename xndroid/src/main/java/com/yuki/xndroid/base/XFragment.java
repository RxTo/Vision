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

    private boolean initialize;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(initLayoutId(), container, false);
            if (useEventBus())
                EventBus.getDefault().register(this);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!initialize) {
            initFragmentComponent(AppUtils.initAppComponent(getContext()));
            initView(savedInstanceState);
            initEvent();
            initialize = true;
        }
    }

    public void initFragmentComponent(AppComponent appComponent) {

    }

    protected abstract int initLayoutId();


    protected void initEvent() {

    }

    public boolean useEventBus() {
        return false;
    }

    public abstract void initView(Bundle savedInstanceState);


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        if (useEventBus())
            EventBus.getDefault().unregister(this);

    }
}
