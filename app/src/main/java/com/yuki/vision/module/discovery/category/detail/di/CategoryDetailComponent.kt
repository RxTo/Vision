package com.yuki.vision.module.discovery.category.detail.di

import com.yuki.vision.module.discovery.category.detail.CategoryDetailActivity
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.ActivityScope

import dagger.Component

@ActivityScope
@Component(modules = arrayOf(CategoryDetailModule::class), dependencies = arrayOf(AppComponent::class))
interface CategoryDetailComponent {
	
	fun inject(fragment : CategoryDetailActivity)
	
}
