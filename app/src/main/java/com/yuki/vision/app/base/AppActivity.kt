package com.yuki.vision.app.base

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutManager
import android.view.View
import com.yuki.vision.R
import com.yuki.vision.app.adapter.recycler.SlimAdapter
import com.yuki.vision.app.config.AppLoadMore
import com.yuki.vision.app.toast
import com.yuki.vision.app.utils.StatusBarUtil
import com.yuki.xndroid.base.XActivity
import com.yuki.xndroid.base.mvp.IPresenter
import javax.inject.Inject

/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/16
 * 邮箱：125508663@qq.com
 */

abstract class AppActivity<P : IPresenter, E> : XActivity<P>() {
    protected val mLoadingView: View by lazy { View.inflate(this, R.layout.view_loading, null) }
    protected val mErrorView: View by lazy { View.inflate(this, R.layout.view_error, null) }
    protected val mEmptyView: View by lazy { View.inflate(this, R.layout.view_empty, null) }

    protected val adapter: SlimAdapter<E> by lazy { SlimAdapter.create<E>(initRecyclerView()) }
    @Inject
    lateinit var layoutManager: LayoutManager
    @Inject
    lateinit var loadMore: AppLoadMore

    private val recyclerView: RecyclerView by lazy { initRecyclerView() }


    abstract fun initRecyclerView(): RecyclerView

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtil.darkMode(this)

    }


    /**
     * 刷新和加载更多成功时
     * */
    open fun initData(data: List<E>, isRefresh: Boolean) {
        when {
            isRefresh -> {
                adapter.initNew(data)
            }
            else -> {
                adapter.initMore(data)
            }
        }
    }

    fun initDataEnd() {
        adapter.loadMoreEnd()
    }


    /**
     * 刷新和加载更多错误时
     * */
    fun initDataFail(msg: String, isRefresh: Boolean) {
        when {
            isRefresh -> {
                adapter.empty(R.layout.view_error, { requestData(true) })
            }
            else -> {
                adapter.loadMoreFail()
            }
        }
        toast(msg)
    }


    abstract fun requestData(isRefresh: Boolean)


}
