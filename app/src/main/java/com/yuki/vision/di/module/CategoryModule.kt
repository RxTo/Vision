package com.yuki.vision.di.module


import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView.LayoutManager
import com.yuki.vision.app.config.AppLoadMore
import com.yuki.vision.mvp.ui.fragment.CategoryFragment
import com.yuki.xndroid.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class CategoryModule(private val view: CategoryFragment) {
    @FragmentScope
    @Provides
    internal fun provideCategoryView(): CategoryFragment {
        return this.view
    }


    @FragmentScope
    @Provides
    fun provideAppLoadMore(): AppLoadMore {
        return AppLoadMore()
    }

    @FragmentScope
    @Provides
    fun provideLayoutManager(): LayoutManager {
        return GridLayoutManager(view.context, 2)
    }
}
