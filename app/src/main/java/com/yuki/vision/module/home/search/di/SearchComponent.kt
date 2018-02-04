package com.yuki.vision.module.home.search.di

import com.yuki.vision.module.home.search.SearchActivity
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.ActivityScope
import dagger.Component


@ActivityScope
@Component(modules = [(SearchModule::class)], dependencies = [(AppComponent::class)])
interface SearchComponent {
	fun inject(activity : SearchActivity)
	
}
