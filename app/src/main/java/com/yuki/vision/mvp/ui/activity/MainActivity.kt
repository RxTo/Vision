package com.yuki.vision.mvp.ui.activity

import android.graphics.Color
import android.os.Bundle
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.yuki.vision.R
import com.yuki.vision.app.toast
import com.yuki.vision.mvp.ui.fragment.DiscoveryFragment
import com.yuki.vision.mvp.ui.fragment.HomeFragment
import com.yuki.vision.mvp.ui.fragment.HotFragment
import com.yuki.vision.mvp.ui.fragment.MineFragment
import com.yuki.xndroid.base.XActivity
import com.yuki.xndroid.base.XFragment
import com.yuki.xndroid.base.mvp.IPresenter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : XActivity<IPresenter>() {
    private val mFragments by lazy { arrayOfNulls<XFragment<*>>(4) }

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            mFragments[0] = HomeFragment.newInstance()
            mFragments[1] = DiscoveryFragment.newInstance("发现")
            mFragments[2] = HotFragment.newInstance("热门")
            mFragments[3] = MineFragment.newInstance()
            supportDelegate.loadMultipleRootFragment(R.id.contentContainer, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3])
        } else {
            mFragments[0] = findFragment(HomeFragment::class.java)
            mFragments[1] = findFragment(DiscoveryFragment::class.java)
            mFragments[2] = findFragment(HotFragment::class.java)
            mFragments[3] = findFragment(MineFragment::class.java)
        }

        bottomBar.run {
            addItem(AHBottomNavigationItem("首页", R.mipmap.ic_home))
            addItem(AHBottomNavigationItem("发现", R.mipmap.ic_discovery))
            addItem(AHBottomNavigationItem("热点", R.mipmap.ic_hot))
            addItem(AHBottomNavigationItem("我的", R.mipmap.ic_mine))
        }
        bottomBar.defaultBackgroundColor = Color.TRANSPARENT
        bottomBar.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
    }


    override fun initEvent() {
        //底部Tab切换侦听,实现双击回到顶部,在顶部刷新
        bottomBar.setOnTabSelectedListener { position, wasSelected ->
            supportDelegate.showHideFragment(mFragments[position])
            true
        }
    }

    //	实现2s重复点击退出app
    private var mExitTime: Long = 0

    override fun onBackPressedSupport() {
        if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
            super.onBackPressedSupport()
        } else {
            mExitTime = System.currentTimeMillis()
            toast("再按一次退出程序")
        }
    }

    override fun initLayoutId(): Int {
        return R.layout.activity_main
    }
}
