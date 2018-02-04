package com.yuki.vision.module.hot.rank

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.yuki.vision.R
import com.yuki.vision.app.AppFragment
import com.yuki.vision.app.goToVideoPlayer
import com.yuki.vision.module.discovery.category.detail.CategoryDetailAdapter
import com.yuki.vision.module.home.HomeResponse.Issue.Item
import com.yuki.vision.module.hot.rank.di.DaggerRankComponent
import com.yuki.vision.module.hot.rank.di.RankModule
import com.yuki.xndroid.di.component.AppComponent
import kotlinx.android.synthetic.main.fragment_recyclerview.*


class RankFragment : AppFragment<RankPresenter, CategoryDetailAdapter, Item>() {
	override fun initRecyclerView() : RecyclerView {
		return recyclerView
	}
	
	override fun goToActivity(view : View, position : Int) {
		goToVideoPlayer(this._mActivity, view, mAdapter.getItem(position))
	}
	
	override fun requestData(isRefresh : Boolean) {
		mPresenter?.requestRankList(arguments?.getString("url").toString(),isRefresh)
	}
	
	override fun initFragmentComponent(appComponent : AppComponent) {
		DaggerRankComponent //如找不到该类,请编译一下项目
				.builder()
				.appComponent(appComponent)
				.rankModule(RankModule(this))
				.build()
				.inject(this)
	}
	
	public override fun initLayoutId() : Int {
		return R.layout.fragment_recyclerview
	}
	
	
	companion object {
		
		fun newInstance(url : String) : RankFragment {
			val fragment = RankFragment()
			val args = Bundle()
			fragment.arguments = args
			args.putString("url", url)
			return fragment
		}
	}
	
	
}
