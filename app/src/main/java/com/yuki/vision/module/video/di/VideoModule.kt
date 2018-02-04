package com.yuki.vision.module.video.di


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.LayoutManager
import com.yuki.vision.app.AppLoadMore
import com.yuki.vision.module.video.VideoActivity
import com.yuki.vision.module.video.VideoAdapter
import com.yuki.xndroid.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

@ActivityScope
@Module
class VideoModule(private val view : VideoActivity) {
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
	@ActivityScope
	@Provides
	internal fun provideVideoView() : VideoActivity {
		return this.view
	}
	@ActivityScope
	@Provides
	internal fun provideVideoAdapter() : VideoAdapter {
		return VideoAdapter()
	}
}
