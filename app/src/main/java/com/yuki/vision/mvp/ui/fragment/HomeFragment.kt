package com.yuki.vision.mvp.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.ViewSkeletonScreen
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.yuki.vision.R
import com.yuki.vision.app.adapter.GlideImageLoader
import com.yuki.vision.app.adapter.recycler.SlimViewHolder
import com.yuki.vision.app.base.AppFragment
import com.yuki.vision.app.durationFormat
import com.yuki.vision.app.goToVideoPlayer
import com.yuki.vision.app.startActivity
import com.yuki.vision.app.utils.StatusBarUtil
import com.yuki.vision.di.component.DaggerHomeComponent
import com.yuki.vision.di.module.HomeModule
import com.yuki.vision.mvp.model.entity.HomeResponse.Issue.Item
import com.yuki.vision.mvp.presenter.HomePresenter
import com.yuki.vision.mvp.ui.activity.SearchActivity
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.imageLoader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_home_banner.view.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : AppFragment<HomePresenter, Item>() {

    private var isSkeletonShow = true

    private val viewBanner: View by lazy { View.inflate(context, R.layout.item_home_banner, null) }

    private lateinit var skeleton: ViewSkeletonScreen

    override fun hideLoading() {
        refreshLayout.isRefreshing = false
    }

    override fun requestData(isRefresh: Boolean) {
        mPresenter?.initData(isRefresh)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(_mActivity)
        StatusBarUtil.setPaddingSmart(_mActivity, toolbar)
    }

    override fun initFragmentComponent(appComponent: AppComponent?) {
        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .homeModule(HomeModule(this))
                .build()
                .inject(this)
    }

    override fun initRecyclerView(): RecyclerView {
        return recyclerView
    }

    private fun initVideoItem(holder: SlimViewHolder, item: Item) {
        val itemData = item.data
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = ""

        // 作者出处为空，就显获取提供者的信息
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }
        // 加载封页图
        ImageLoader.loadImage(holder.find(R.id.iv_cover_feed), cover, R.mipmap.bg_loading, R.mipmap.bg_error)
        // 如果提供者信息为空，就显示默认
        ImageLoader.loadRoundImage(holder.find(R.id.view_avatar), avatar, R.mipmap.default_avatar, R.mipmap.default_avatar)

        itemData?.tags?.take(4)?.forEach {
            tagText += (it.name + "/")
        }
        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)

        tagText += timeFormat

        holder.text(R.id.tv_tag, tagText)
                .text(R.id.tv_title, itemData?.title ?: "")
                .text(R.id.tv_category, itemData?.category)

    }

    override fun initView(savedInstanceState: Bundle?) {
        adapter
                .head(viewBanner)
                .layout(layoutManager)
                .multiple({ item, position ->
                    when (item.itemType) {
                        Item.TYPE_VIDEO -> return@multiple R.layout.item_home_content
                        Item.TYPE_TEXT_HEAD -> return@multiple R.layout.item_home_header
                        else -> {
                            return@multiple 0
                        }
                    }
                })
                .register(R.layout.item_home_content, { holder, data, position ->
                    initVideoItem(holder, data)
                })
                .register(R.layout.item_home_header, { holder, data, position ->
                    holder.text(R.id.tvHeader, data.data?.text)
                })
                .loadMore({
                    requestData(false)
                })
                .click({ holder, view, position ->
                    goToVideoPlayer(_mActivity, view, position as Item?)
                })
        viewBanner.banner?.run {
            //设置banner样式
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            //设置图片加载器
            setImageLoader(GlideImageLoader())
            //设置banner动画效果
            setBannerAnimation(Transformer.Stack)
            //设置自动轮播，默认为true
            isAutoPlay(true)
            //设置轮播时间
            setDelayTime(3000)
            //设置指示器位置（当banner模式中有指示器时）
            setIndicatorGravity(BannerConfig.RIGHT)
        }
        requestData(true)
        skeleton = Skeleton.bind(root)
                .load(R.layout.view_skeleton)
                .duration(800)
                .color(R.color.light_transparent)
                .angle(10)
                .show()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initEvent() {
        super.initEvent()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //toolbar根据滚动进行隐藏和显示
                val currentVisibleItemPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                when {
                //当前为第一项则背景设置为透明
                    currentVisibleItemPosition < 2 -> {
                        toolbar.setBackgroundColor(resources.getColor(android.R.color.transparent))
                        iv_search.setImageResource(R.mipmap.ic_action_search_white)
                        tv_header_title.text = ""
                    }
                //不在第一项,同时有数据就将背景设置成白色
                    adapter.itemCount > 1 -> {
                        toolbar.setBackgroundColor(resources.getColor(android.R.color.white))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val item = adapter.getItem(currentVisibleItemPosition - 1)
                        //日期看不见再更换
                        if (item.type == "textHeader") {
                            tv_header_title.text = item.data?.text
                        } else tv_header_title.text = simpleDateFormat.format(item.data?.date)
                    }
                }
            }
        })
        refreshLayout.setOnRefreshListener {
            requestData(true)
        }
        iv_search.setOnClickListener {
            goToSearch()
        }
    }

    fun hideSkeleton() {
        if (isSkeletonShow) {
            isSkeletonShow = false
            skeleton.hide()
        }
    }


    /**
     * 打开搜索Activity,根据安装手机版本判断是否启动共享动画
     * */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun goToSearch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity?.let {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(it, iv_search, iv_search.transitionName)
                it.startActivity<SearchActivity>(options)
            }
        } else {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }

    /**
     * 格式化日期
     * */
    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    fun initBanner(bannerList: ArrayList<String>, bannerTitleList: ArrayList<String>) {
        //设置图片集合
        viewBanner.banner?.run {
            setImages(bannerList)
            //设置标题集合（当banner样式有显示title时）
            setBannerTitles(bannerTitleList)
            start()
        }
    }


    public override fun initLayoutId(): Int {
        return R.layout.fragment_home
    }

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        viewBanner.banner?.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        viewBanner.banner?.stopAutoPlay()
    }


}
