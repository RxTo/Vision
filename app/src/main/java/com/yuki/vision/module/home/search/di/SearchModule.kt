package com.yuki.vision.module.home.search.di


import android.support.v7.widget.LinearLayoutManager
import com.yuki.vision.app.AppLoadMore
import com.yuki.vision.module.discovery.category.detail.CategoryDetailAdapter
import com.yuki.vision.module.home.search.HotSearchAdapter
import com.yuki.vision.module.home.search.SearchActivity
import com.yuki.xndroid.di.scope.ActivityScope

import dagger.Module
import dagger.Provides


@Module
class SearchModule(private val view : SearchActivity) {
	@ActivityScope
	@Provides
	fun provideAppLoadMore() : AppLoadMore {
		return AppLoadMore()
	}
	
	@ActivityScope
	@Provides
	fun provideLayoutManager() : LinearLayoutManager {
		return LinearLayoutManager(view)
	}
	
	@ActivityScope
	@Provides
	internal fun provideSearchView() : SearchActivity {
		return this.view
	}
	
	@ActivityScope
	@Provides
	fun provideSearchAdapter() : CategoryDetailAdapter {
		return CategoryDetailAdapter()
	}
	
	@ActivityScope
	@Provides
	fun provideHotSearchAdapter() : HotSearchAdapter {
		return HotSearchAdapter()
	}
}
