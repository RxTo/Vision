package com.yuki.vision.di.component

import com.yuki.vision.di.module.RankModule
import com.yuki.vision.mvp.ui.fragment.RankFragment
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.di.scope.FragmentScope
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(RankModule::class), dependencies = arrayOf(AppComponent::class))
interface RankComponent {

    fun inject(fragment: RankFragment)

}
