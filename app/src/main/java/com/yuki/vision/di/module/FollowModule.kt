package com.yuki.vision.di.module


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.LayoutManager
import com.yuki.vision.app.config.AppLoadMore
import com.yuki.vision.mvp.ui.fragment.FollowFragment
import com.yuki.xndroid.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class FollowModule(private val view: FollowFragment) {
    @FragmentScope
    @Provides
    internal fun provideFollowView(): FollowFragment {
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
        return LinearLayoutManager(view.context)
    }
}
