package com.yuki.vision.app.adapter.recycler.ex;

import com.yuki.vision.app.adapter.recycler.SlimViewHolder;

/**
 * 项目：SlimAdapter
 * 作者：Yuki - 2018/3/5
 */
public interface SlimInjector<T> {
    void onInject(SlimViewHolder holder, T item, int position);
}
