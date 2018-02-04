package com.yuki.vision.module.home

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
import com.yuki.vision.app.AppFragment
import com.yuki.vision.app.goToVideoPlayer
import com.yuki.vision.app.startActivity
import com.yuki.vision.module.home.HomeResponse.Issue.Item
import com.yuki.vision.module.home.HomeResponse.Issue.Item.Companion.TYPE_TEXT_HEAD
import com.yuki.vision.module.home.di.DaggerHomeComponent
import com.yuki.vision.module.home.di.HomeModule
import com.yuki.vision.module.home.search.SearchActivity
import com.yuki.xndroid.di.component.AppComponent
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_home_banner.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : AppFragment<HomePresenter, HomeAdapter, Item>() {
	override fun goToActivity(view : View, position : Int) {
		goToVideoPlayer(this._mActivity, view, mAdapter.getItem(position))
		
	}
	
	private val viewBanner : View by lazy { View.inflate(context, R.layout.item_home_banner, null) }
	private lateinit var skeleton : ViewSkeletonScreen
	
	override fun initFragmentComponent(appComponent : AppComponent?) {
		DaggerHomeComponent.builder()
				.appComponent(appComponent)
				.homeModule(HomeModule(this))
				.build()
				.inject(this)
	}
	
	
	public override fun initView(savedInstanceState : Bundle?) {
		super.initView(savedInstanceState)
		mAdapter.addHeaderView(viewBanner)
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
		
		skeleton = Skeleton.bind(root)
				.load(R.layout.view_skeleton)
				.duration(800)
				.color(R.color.light_transparent)
				.angle(10)
				.show()
		
	}
	
	private var isSkeletonShow = true
	
	fun hideSkeleton() {
		if (isSkeletonShow) {
			isSkeletonShow = false
			skeleton.hide()
		}
	}
	
	override fun hideLoading() {
		refreshLayout.isRefreshing = false
	}
	
	
	@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
	override fun initEvent() {
		super.initEvent()
		refreshLayout.setOnRefreshListener {
			requestData(true)
		}
		
		iv_search.setOnClickListener { goToSearch() }
		
		viewBanner.banner.setOnBannerListener {
			goToVideoPlayer(this._mActivity, viewBanner.banner, mPresenter?.bannerList?.get(it))
		}
		
		recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			override fun onScrolled(recyclerView : RecyclerView?, dx : Int, dy : Int) {
				super.onScrolled(recyclerView, dx, dy)
				//toolbar根据滚动进行隐藏和显示
				val currentVisibleItemPosition = (mLayoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
				when {
				//当前为第一项则背景设置为透明
					currentVisibleItemPosition < 2 -> {
						blurView.visibility = View.GONE
						iv_search.setImageResource(R.mipmap.ic_action_search_white)
						tv_header_title.text = ""
					}
				//不在第一项,同时有数据就将背景设置成白色
					mAdapter.itemCount > 1 -> {
						blurView.visibility = View.VISIBLE
						iv_search.setImageResource(R.mipmap.ic_action_search_black)
						val item = mAdapter.data[currentVisibleItemPosition - 1]
						//日期看不见再更换
						if (mAdapter.getItemViewType(currentVisibleItemPosition - 1) == TYPE_TEXT_HEAD) {
							tv_header_title.text = simpleDateFormat.format(item.data?.date)
						}
					}
				}
			}
		})
	}
	
	
	override fun initRecyclerView() : RecyclerView {
		return recyclerView
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
	
	fun initBanner(bannerList : ArrayList<String>, bannerTitleList : ArrayList<String>) {
		//设置图片集合
		viewBanner.banner?.run {
			setImages(bannerList)
			//设置标题集合（当banner样式有显示title时）
			setBannerTitles(bannerTitleList)
			start()
		}
	}
	
	override fun requestData(isRefresh : Boolean) {
		mPresenter?.initData(isRefresh)
	}
	
	
	public override fun initLayoutId() : Int {
		return R.layout.fragment_home
	}
	
	companion object {
		fun newInstance() : HomeFragment {
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
