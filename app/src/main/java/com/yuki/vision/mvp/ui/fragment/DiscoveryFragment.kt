package com.yuki.vision.mvp.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.widget.LinearLayout
import com.yuki.vision.R
import com.yuki.vision.app.adapter.ViewPagerAdapter
import com.yuki.vision.app.utils.StatusBarUtil
import com.yuki.xndroid.base.XFragment
import com.yuki.xndroid.base.mvp.IPresenter
import com.yuki.xndroid.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_viewpager.*
import java.lang.reflect.Field


class DiscoveryFragment : XFragment<IPresenter>() {

    private val fragments by lazy { ArrayList<Fragment>() }

    public override fun initLayoutId(): Int {
        return R.layout.fragment_viewpager
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StatusBarUtil.darkMode(_mActivity)
        StatusBarUtil.setPaddingSmart(_mActivity, mTabLayout)
    }

    override fun initView(savedInstanceState: Bundle?) {
        fragments.add(FollowFragment.newInstance("关注"))
        fragments.add(CategoryFragment.newInstance("分类"))


        mViewPager.adapter = ViewPagerAdapter(fragments, childFragmentManager)
        mTabLayout.setupWithViewPager(mViewPager)
        setUpIndicatorWidth(mTabLayout)
    }

    companion object {
        fun newInstance(title: String): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            val args = Bundle()
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setUpIndicatorWidth(tabLayout: TabLayout) {
        val tabLayoutClass = tabLayout.javaClass
        var tabStrip: Field? = null
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip")
            tabStrip!!.isAccessible = true
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        var layout: LinearLayout? = null
        try {
            if (tabStrip != null) {
                layout = tabStrip.get(tabLayout) as LinearLayout
            }
            for (i in 0 until layout!!.childCount) {
                val child = layout.getChildAt(i)
                child.setPadding(0, 0, 0, 0)
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.marginStart = AppUtils.dip2px(this.context, 50f)
                    params.marginEnd = AppUtils.dip2px(this.context, 50f)
                }
                child.layoutParams = params
                child.invalidate()
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }
}
