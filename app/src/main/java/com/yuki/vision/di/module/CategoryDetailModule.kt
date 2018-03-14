package com.yuki.vision.di.module


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.LayoutManager
import com.yuki.vision.app.config.AppLoadMore
import com.yuki.vision.mvp.ui.activity.CategoryDetailActivity
import com.yuki.xndroid.di.scope.ActivityScope
import dagger.Module
import dagger.Provides


@Module
class CategoryDetailModule(private val view: CategoryDetailActivity) {

    @ActivityScope
    @Provides
    internal fun provideCategoryDetailView(): CategoryDetailActivity {
        return this.view
    }


    @ActivityScope
    @Provides
    fun provideAppLoadMore(): AppLoadMore {
        return AppLoadMore()
    }

    @ActivityScope
    @Provides
    fun provideLayoutManager(): LayoutManager {
        return LinearLayoutManager(view)
    }
}
