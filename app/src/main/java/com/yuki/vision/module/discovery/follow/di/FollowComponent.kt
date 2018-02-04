package com.yuki.vision.module.discovery.follow.di

import com.yuki.vision.module.discovery.follow.FollowFragment
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.FragmentScope

import dagger.Component

@FragmentScope
@Component(modules = arrayOf(FollowModule::class), dependencies = arrayOf(AppComponent::class))
interface FollowComponent {
	
	fun inject(fragment : FollowFragment)
	
}
