package com.yuki.xndroid.adapter;


import com.yuki.xndroid.adapter.injector.IViewInjector;

public interface SlimInjector<T> {
    void onInject(T data, IViewInjector injector);
}
