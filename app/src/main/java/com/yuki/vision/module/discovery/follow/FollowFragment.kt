package com.yuki.vision.module.discovery.follow

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.yuki.vision.R
import com.yuki.vision.app.AppFragment
import com.yuki.vision.module.discovery.follow.di.DaggerFollowComponent
import com.yuki.vision.module.discovery.follow.di.FollowModule
import com.yuki.vision.module.home.HomeResponse.Issue.Item
import com.yuki.xndroid.di.component.AppComponent
import kotlinx.android.synthetic.main.fragment_recyclerview.*


class FollowFragment : AppFragment<FollowPresenter, FollowAdapter, Item>() {
	override fun goToActivity(view : View, position : Int) {
		//已由子RecyclerView处理
	}
	
	public override fun initView(savedInstanceState : Bundle?) {
		super.initView(savedInstanceState)
		mAdapter.emptyView = mLoadingView
	}
	
	override fun initRecyclerView() : RecyclerView {
		return recyclerView
	}
	
	override fun requestData(isRefresh : Boolean) {
		mPresenter?.initData(isRefresh)
	}
	
	
	override fun initFragmentComponent(appComponent : AppComponent) {
		DaggerFollowComponent //如找不到该类,请编译一下项目
				.builder()
				.appComponent(appComponent)
				.followModule(FollowModule(this))
				.build()
				.inject(this)
	}
	
	public override fun initLayoutId() : Int {
		return R.layout.fragment_recyclerview
	}
	
	companion object {
		fun newInstance(title : String) : FollowFragment {
			val fragment = FollowFragment()
			val args = Bundle()
			fragment.arguments = args
			args.putString("title", title)
			return fragment
		}
	}
	
}
