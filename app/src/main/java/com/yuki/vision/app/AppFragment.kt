package com.yuki.vision.app

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutManager
import android.support.v7.widget.RecyclerView.OnScrollListener
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuki.vision.R
import com.yuki.xndroid.base.XFragment
import com.yuki.xndroid.base.mvp.IPresenter
import javax.inject.Inject

/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/16
 * 邮箱：125508663@qq.com
 */

abstract class AppFragment<P : IPresenter, T : BaseQuickAdapter<D, BaseViewHolder>, D> : XFragment<P>() {
	private var mInAtTop = true
	private var mScrollTotal : Int = 0
	protected val mLoadingView : View by lazy { View.inflate(context, R.layout.view_loading, null) }
	protected val mErrorView : View by lazy { View.inflate(context, R.layout.view_error, null) }
	@Inject
	lateinit var mAdapter : T
	@Inject
	lateinit var mLayoutManager : LayoutManager
	@Inject
	lateinit var loadMore : AppLoadMore
	
	private val mRecyclerView : RecyclerView by lazy { initRecyclerView() }
	
	
	abstract fun initRecyclerView() : RecyclerView
	
	override fun initView(savedInstanceState : Bundle?) {
		mAdapter.setLoadMoreView(loadMore)
		mAdapter.isFirstOnly(true)
		mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
		mRecyclerView.layoutManager = mLayoutManager
		mRecyclerView.setHasFixedSize(true)
		mRecyclerView.adapter = mAdapter
		mAdapter.emptyView = mLoadingView
		requestData(true)
	}
	
	
	/**
	 * 刷新和加载更多成功时
	 * */
	fun initData(data : List<D>, isRefresh : Boolean) {
		when {
			isRefresh -> {
				hideLoading()
				mAdapter.setNewData(data)
			}
			else -> {
				mAdapter.addData(data)
				mAdapter.loadMoreComplete()
			}
		}
	}
	
	
	override fun initEvent() {
		if (useLoadMore())
			mAdapter.setOnLoadMoreListener({
				requestData(false)
			}, mRecyclerView)
		
		mAdapter.setOnItemClickListener({ adapter, view, position ->
			goToActivity(view, position)
		})
		
		mRecyclerView.addOnScrollListener(object : OnScrollListener() {
			override fun onScrolled(recyclerView : RecyclerView?, dx : Int, dy : Int) {
				super.onScrolled(recyclerView, dx, dy)
				mScrollTotal += dy
				mInAtTop = mScrollTotal <= 0
			}
		})
		mErrorView.setOnClickListener {
			mAdapter.emptyView = mLoadingView
			requestData(true)
		}
		
	}
	
	abstract fun goToActivity(view : View, position : Int)
	
	fun initDataEnd() {
		mAdapter.loadMoreEnd()
	}
	
	/**
	 * 设置是否加载更多
	 * */
	open fun useLoadMore() : Boolean {
		return true
	}
	
	/**
	 * 刷新和加载更多错误时
	 * */
	fun initDataFail(msg : String, isRefresh : Boolean) {
		when {
			isRefresh -> {
				if (mAdapter.itemCount > 1) hideLoading() else mAdapter.setEmptyView(mErrorView)
			}
			else -> {
				mAdapter.loadMoreFail()
			}
		}
		toast(msg)
	}
	
	open fun hideLoading() {
	
	
	}
	
	
	abstract fun requestData(isRefresh : Boolean)
	
	/**
	 * bottom的重复点击Tab刷新界面,若不在顶部则回到顶部
	 * */
	fun refreshByTab() {
		if (mInAtTop) {
			requestData(true)
		} else
			mRecyclerView.smoothScrollToPosition(0)
	}
	
}
