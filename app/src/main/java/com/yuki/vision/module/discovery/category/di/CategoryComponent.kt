package com.yuki.vision.module.discovery.category.di

import com.yuki.vision.module.discovery.category.CategoryFragment
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.FragmentScope

import dagger.Component

@FragmentScope
@Component(modules = arrayOf(CategoryModule::class), dependencies = arrayOf(AppComponent::class))
interface CategoryComponent {
	
	fun inject(fragment : CategoryFragment)
	
}
