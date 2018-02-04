package com.yuki.vision.module.hot.di

import com.yuki.vision.module.hot.HotFragment
import com.yuki.xndroid.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * 项目：Vision
 * 作者：Yuki - 2018/2/1
 * 邮箱：125508663@qq.com
 **/
@Module
class HotModule(private val hotFragment : HotFragment) {
	@FragmentScope
	@Provides
	internal fun provideHotView() : HotFragment {
		return this.hotFragment
	}
}