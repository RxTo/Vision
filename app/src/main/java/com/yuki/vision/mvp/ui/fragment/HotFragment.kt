package com.yuki.vision.mvp.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.ethanhua.skeleton.ViewReplacer
import com.yuki.vision.R
import com.yuki.vision.app.adapter.ViewPagerAdapter
import com.yuki.vision.app.toast
import com.yuki.vision.app.utils.StatusBarUtil
import com.yuki.vision.di.component.DaggerHotComponent
import com.yuki.vision.di.module.HotModule
import com.yuki.vision.mvp.model.entity.TabInfoResponse
import com.yuki.vision.mvp.presenter.HotPresenter
import com.yuki.xndroid.base.XFragment
import com.yuki.xndroid.di.component.AppComponent
import kotlinx.android.synthetic.main.fragment_viewpager.*


class HotFragment : XFragment<HotPresenter>() {
    private lateinit var viewReplacer: ViewReplacer
    private val tabList by lazy { ArrayList<String>() }
    private val fragments by lazy { ArrayList<Fragment>() }


    override fun initFragmentComponent(appComponent: AppComponent?) {
        DaggerHotComponent.builder()
                .appComponent(appComponent)
                .hotModule(HotModule(this))
                .build().inject(this)
    }

    public override fun initLayoutId(): Int {
        return R.layout.fragment_viewpager
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StatusBarUtil.darkMode(_mActivity)
        StatusBarUtil.setPaddingSmart(_mActivity, mTabLayout)
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewReplacer = ViewReplacer(mViewPager)
        viewReplacer.replace(R.layout.view_loading)
        mPresenter?.getTabInfo()
    }

    companion object {
        fun newInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val args = Bundle()
            fragment.arguments = args
            args.putString("title", title)
            return fragment
        }
    }


    fun initData(data: TabInfoResponse?) {
        viewReplacer.restore()
        data?.tabInfo?.tabList?.map {
            fragments.add(RankFragment.newInstance(it.apiUrl))
            tabList.add(it.name)
        }
        mViewPager.adapter = ViewPagerAdapter(fragments, tabList, childFragmentManager)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    fun initDataFail(errorMsg: String?) {
        toast(errorMsg.toString())
        val view = View.inflate(this.context, R.layout.view_error, null)
        viewReplacer.replace(view)
        view.setOnClickListener { mPresenter?.getTabInfo() }
        viewReplacer.replace(R.layout.view_loading)
    }

}
