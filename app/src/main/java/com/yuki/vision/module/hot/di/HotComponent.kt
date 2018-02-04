package com.yuki.vision.module.hot.di

import com.yuki.vision.module.hot.HotFragment
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.FragmentScope
import dagger.Component

/**
 * 项目：Vision
 * 作者：Yuki - 2018/2/1
 * 邮箱：125508663@qq.com
 **/

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [HotModule::class])
interface HotComponent {
	fun inject(hotFragment : HotFragment)
}