package com.yuki.vision.module.discovery.category.di


import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView.LayoutManager
import com.yuki.vision.app.AppLoadMore
import com.yuki.vision.module.discovery.category.CategoryAdapter
import com.yuki.vision.module.discovery.category.CategoryFragment
import com.yuki.xndroid.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class CategoryModule(private val view : CategoryFragment) {
	@FragmentScope
	@Provides
	internal fun provideCategoryView() : CategoryFragment {
		return this.view
	}
	
	@FragmentScope
	@Provides
	fun provideAdapter() : CategoryAdapter {
		return CategoryAdapter()
	}
	
	@FragmentScope
	@Provides
	fun provideAppLoadMore() : AppLoadMore {
		return AppLoadMore()
	}
	
	@FragmentScope
	@Provides
	fun provideLayoutManager() : LayoutManager {
		return GridLayoutManager(view.context, 2)
	}
}
