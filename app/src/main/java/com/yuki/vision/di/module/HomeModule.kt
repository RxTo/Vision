package com.yuki.vision.di.module

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.LayoutManager
import com.yuki.vision.app.config.AppLoadMore
import com.yuki.vision.mvp.ui.fragment.HomeFragment
import com.yuki.xndroid.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * 项目：Vision
 * 作者：Yuki - 2018/1/31
 * 邮箱：125508663@qq.com
 **/

@Module
class HomeModule(private val homeFragment: HomeFragment) {

    @FragmentScope
    @Provides
    fun provideHomeFragment(): HomeFragment {
        return homeFragment
    }

    @FragmentScope
    @Provides
    fun provideAppLoadMore(): AppLoadMore {
        return AppLoadMore()
    }

    @FragmentScope
    @Provides
    fun provideLayoutManager(): LayoutManager {
        return LinearLayoutManager(homeFragment.context)
    }


}