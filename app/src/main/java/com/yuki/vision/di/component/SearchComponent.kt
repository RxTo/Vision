package com.yuki.vision.di.component

import com.yuki.vision.di.module.SearchModule
import com.yuki.vision.mvp.ui.activity.SearchActivity
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.ActivityScope
import dagger.Component


@ActivityScope
@Component(modules = [(SearchModule::class)], dependencies = [(AppComponent::class)])
interface SearchComponent {
    fun inject(activity: SearchActivity)

}
