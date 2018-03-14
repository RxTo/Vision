package com.yuki.vision.di.component

import com.yuki.vision.di.module.CategoryModule
import com.yuki.vision.mvp.ui.fragment.CategoryFragment
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.FragmentScope

import dagger.Component

@FragmentScope
@Component(modules = arrayOf(CategoryModule::class), dependencies = arrayOf(AppComponent::class))
interface CategoryComponent {

    fun inject(fragment: CategoryFragment)

}
