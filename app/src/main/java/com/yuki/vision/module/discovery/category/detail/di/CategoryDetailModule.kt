package com.yuki.vision.module.discovery.category.detail.di


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.LayoutManager
import com.yuki.vision.app.AppLoadMore
import com.yuki.vision.module.discovery.category.detail.CategoryDetailActivity
import com.yuki.vision.module.discovery.category.detail.CategoryDetailAdapter
import com.yuki.xndroid.di.scope.ActivityScope
import dagger.Module
import dagger.Provides


@Module
class CategoryDetailModule(private val view : CategoryDetailActivity) {
	
	@ActivityScope
	@Provides
	internal fun provideCategoryDetailView() : CategoryDetailActivity {
		return this.view
	}
	@ActivityScope
	@Provides
	fun provideAdapter() : CategoryDetailAdapter {
		return CategoryDetailAdapter()
	}
	
	@ActivityScope
	@Provides
	fun provideAppLoadMore() : AppLoadMore {
		return AppLoadMore()
	}
	
	@ActivityScope
	@Provides
	fun provideLayoutManager() : LayoutManager {
		return LinearLayoutManager(view)
	}
}
