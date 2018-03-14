package com.yuki.vision.app.adapter.recycler.ex;

import android.support.v7.widget.GridLayoutManager;

/**
 * 项目：SlimAdapter
 * 作者：Yuki - 2018/3/12
 */


public interface SpanSizeLookup {
    int getSpanSize(GridLayoutManager gridLayoutManager, int position);
}