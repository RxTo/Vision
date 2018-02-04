package com.yuki.vision.module.video.di

import com.yuki.vision.module.video.VideoActivity
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(VideoModule::class), dependencies = arrayOf(AppComponent::class))
interface VideoComponent {
	fun inject(activity : VideoActivity)
	
}
