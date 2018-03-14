package com.yuki.vision.di.component

import com.yuki.vision.di.module.FollowModule
import com.yuki.vision.mvp.ui.fragment.FollowFragment
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.FragmentScope

import dagger.Component

@FragmentScope
@Component(modules = arrayOf(FollowModule::class), dependencies = arrayOf(AppComponent::class))
interface FollowComponent {

    fun inject(fragment: FollowFragment)

}
