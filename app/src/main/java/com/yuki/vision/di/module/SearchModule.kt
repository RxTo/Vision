package com.yuki.vision.di.module


import android.support.v7.widget.LinearLayoutManager
import com.yuki.vision.app.config.AppLoadMore
import com.yuki.vision.mvp.ui.activity.SearchActivity
import com.yuki.xndroid.di.scope.ActivityScope
import dagger.Module
import dagger.Provides


@Module
class SearchModule(private val view: SearchActivity) {
    @ActivityScope
    @Provides
    fun provideAppLoadMore(): AppLoadMore {
        return AppLoadMore()
    }

    @ActivityScope
    @Provides
    fun provideLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(view)
    }

    @ActivityScope
    @Provides
    internal fun provideSearchView(): SearchActivity {
        return this.view
    }


}
