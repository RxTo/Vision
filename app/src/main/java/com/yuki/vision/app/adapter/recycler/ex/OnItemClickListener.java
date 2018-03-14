package com.yuki.vision.app.adapter.recycler.ex;

import android.view.View;

import com.yuki.vision.app.adapter.recycler.SlimViewHolder;

/**
 * 项目：SlimAdapter
 * 作者：Yuki - 2018/3/6
 */
public interface OnItemClickListener {
    void onItemClick(SlimViewHolder holder, View view, Object item);
}
