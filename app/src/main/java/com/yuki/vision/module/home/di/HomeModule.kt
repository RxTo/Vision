package com.yuki.vision.module.home.di

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.LayoutManager
import com.yuki.vision.app.AppLoadMore
import com.yuki.vision.module.home.HomeAdapter
import com.yuki.vision.module.home.HomeFragment
import com.yuki.xndroid.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * 项目：Vision
 * 作者：Yuki - 2018/1/31
 * 邮箱：125508663@qq.com
 **/

@Module
class HomeModule(private val homeFragment : HomeFragment) {
	
	@FragmentScope
	@Provides
	fun provideHomeFragment() : HomeFragment {
		return homeFragment
	}
	
	@FragmentScope
	@Provides
	fun provideAppLoadMore() : AppLoadMore {
		return AppLoadMore()
	}
	
	@FragmentScope
	@Provides
	fun provideLayoutManager() : LayoutManager {
		return LinearLayoutManager(homeFragment.context)
	}
	
	@FragmentScope
	@Provides
	fun provideHomeAdapter() : HomeAdapter {
		return HomeAdapter()
	}
	
}