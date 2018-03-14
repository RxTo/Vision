package com.yuki.vision.di.component

import com.yuki.vision.di.module.HomeModule
import com.yuki.vision.mvp.ui.fragment.HomeFragment
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.FragmentScope
import dagger.Component

/**
 * 项目：Vision
 * 作者：Yuki - 2018/1/31
 * 邮箱：125508663@qq.com
 **/

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [HomeModule::class])
interface HomeComponent {
    fun inject(homeFragment: HomeFragment)
}