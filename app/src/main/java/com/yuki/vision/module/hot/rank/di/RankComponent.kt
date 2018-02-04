package com.yuki.vision.module.hot.rank.di

import com.yuki.vision.module.hot.rank.RankFragment
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.FragmentScope

import dagger.Component

@FragmentScope
@Component(modules = arrayOf(RankModule::class), dependencies = arrayOf(AppComponent::class))
interface RankComponent {
	
	fun inject(fragment : RankFragment)
	
}
