package com.yuki.vision.di.component

import com.yuki.vision.di.module.VideoModule
import com.yuki.vision.mvp.ui.activity.VideoActivity
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(VideoModule::class), dependencies = arrayOf(AppComponent::class))
interface VideoComponent {
    fun inject(activity: VideoActivity)

}
