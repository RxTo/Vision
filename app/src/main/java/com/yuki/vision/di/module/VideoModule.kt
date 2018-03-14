package com.yuki.vision.di.module


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.LayoutManager
import com.yuki.vision.app.config.AppLoadMore
import com.yuki.vision.mvp.ui.activity.VideoActivity
import com.yuki.xndroid.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

@ActivityScope
@Module
class VideoModule(private val view: VideoActivity) {
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

    @ActivityScope
    @Provides
    internal fun provideVideoView(): VideoActivity {
        return this.view
    }

}
