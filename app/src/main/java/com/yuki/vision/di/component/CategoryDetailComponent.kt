package com.yuki.vision.di.component

import com.yuki.vision.di.module.CategoryDetailModule
import com.yuki.vision.mvp.ui.activity.CategoryDetailActivity
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.ActivityScope

import dagger.Component

@ActivityScope
@Component(modules = arrayOf(CategoryDetailModule::class), dependencies = arrayOf(AppComponent::class))
interface CategoryDetailComponent {

    fun inject(fragment: CategoryDetailActivity)

}
