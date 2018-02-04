package com.yuki.vision.module.discovery.follow.di


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.LayoutManager
import com.yuki.vision.app.AppLoadMore
import com.yuki.vision.module.discovery.follow.FollowAdapter
import com.yuki.vision.module.discovery.follow.FollowFragment
import com.yuki.xndroid.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

@Module
class FollowModule(private val view : FollowFragment) {
	@FragmentScope
	@Provides
	internal fun provideFollowView() : FollowFragment {
		return this.view
	}
	
	@FragmentScope
	@Provides
	internal fun provideAdapter() : FollowAdapter {
		return FollowAdapter()
	}
	
	@FragmentScope
	@Provides
	fun provideAppLoadMore() : AppLoadMore {
		return AppLoadMore()
	}
	
	@FragmentScope
	@Provides
	fun provideLayoutManager() : LayoutManager {
		return LinearLayoutManager(view.context)
	}
}
