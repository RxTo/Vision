package com.yuki.vision.app.base

import android.support.v7.widget.RecyclerView
import com.yuki.vision.R
import com.yuki.vision.app.adapter.recycler.SlimAdapter
import com.yuki.vision.app.config.AppLoadMore
import com.yuki.vision.app.toast
import com.yuki.xndroid.base.XFragment
import com.yuki.xndroid.base.mvp.IPresenter
import javax.inject.Inject

/**
 * 项目：Vision
 * 作者：Yuki - 2018/3/5
 * 邮箱：125508663@qq.com
 */
abstract class AppFragment<P : IPresenter, E> : XFragment<P>() {
    private var atTop = true
    private var scrollTotal: Int = 0

    protected val adapter: SlimAdapter<E> by lazy { SlimAdapter.create<E>(initRecyclerView()) }
    @Inject
    lateinit var layoutManager: RecyclerView.LayoutManager
    @Inject
    lateinit var loadMore: AppLoadMore

    private val recyclerView: RecyclerView by lazy { initRecyclerView() }

    abstract fun initRecyclerView(): RecyclerView


    /**
     * 刷新和加载更多成功时
     * */
    fun initData(data: List<E>, isRefresh: Boolean) {
        when {
            isRefresh -> {
                hideLoading()
                adapter.initNew(data)
            }
            else -> {
                adapter.initMore(data)
            }
        }
    }

    open fun hideLoading() {

    }


    override fun initEvent() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollTotal += dy
                atTop = scrollTotal <= 0
            }
        })
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
                if (adapter.itemCount > 1)
                    hideLoading()
                else
                    adapter.empty(R.layout.view_error, { requestData(true) })
                hideLoading()
            }
            else -> {
                adapter.loadMoreFail()
            }
        }
        toast(msg)
    }

    abstract fun requestData(isRefresh: Boolean)

    /**
     * bottom的重复点击Tab刷新界面,若不在顶部则回到顶部
     * */
    fun refreshByTab() {
        if (atTop) {
            requestData(true)
        } else
            recyclerView.smoothScrollToPosition(0)
    }
}