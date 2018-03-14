package com.yuki.vision.app.widget

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.State
import android.view.View
import com.yuki.xndroid.utils.AppUtils


/**
 * 项目：Vision
 * 作者：Yuki - 2018/1/31
 * 邮箱：125508663@qq.com
 */

class RecycleViewDivider(val context: Context) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val childAdapterPosition = parent?.getChildAdapterPosition(view)
        val lastCount = parent?.adapter?.itemCount?.minus(1)
        if (outRect?.right == 0 && childAdapterPosition != lastCount) {
            outRect.set(0, 0, AppUtils.dip2px(context, 10f), 0)
        }

    }
}
