package com.yuki.vision.app

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuki.vision.R
import com.yuki.vision.utils.StatusBarUtil
import com.yuki.xndroid.base.XActivity
import com.yuki.xndroid.base.mvp.IPresenter
import javax.inject.Inject

/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/16
 * 邮箱：125508663@qq.com
 */

abstract class AppActivity<P : IPresenter, T : BaseQuickAdapter<D, BaseViewHolder>, D> : XActivity<P>() {
	protected val mLoadingView : View by lazy { View.inflate(this, R.layout.view_loading, null) }
	protected val mErrorView : View by lazy { View.inflate(this, R.layout.view_error, null) }
	protected val mEmptyView : View by lazy { View.inflate(this, R.layout.view_empty, null) }
	
	@Inject
	lateinit var mAdapter : T
	@Inject
	lateinit var mLayoutManager : LayoutManager
	@Inject
	lateinit var loadMore : AppLoadMore
	
	private val mRecyclerView : RecyclerView by lazy { initRecyclerView() }
	
	
	abstract fun initRecyclerView() : RecyclerView
	
	override fun initView(savedInstanceState : Bundle?) {
		StatusBarUtil.darkMode(this)
		mAdapter.setLoadMoreView(loadMore)
		mRecyclerView.layoutManager = mLayoutManager
		mRecyclerView.setHasFixedSize(true)
		mRecyclerView.adapter = mAdapter
	}
	
	
	/**
	 * 刷新和加载更多成功时
	 * */
	open fun initData(data : List<D>, isRefresh : Boolean) {
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
		mAdapter.setOnLoadMoreListener({
			requestData(false)
		}, mRecyclerView)
		mErrorView.setOnClickListener {
			mAdapter.emptyView = mLoadingView
			requestData(true)
		}
		
	}
	
	fun initDataEnd() {
		mAdapter.loadMoreEnd()
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
	
	
}
