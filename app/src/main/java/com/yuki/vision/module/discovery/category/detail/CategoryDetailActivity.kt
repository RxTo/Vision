package com.yuki.vision.module.discovery.category.detail

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.yuki.vision.R
import com.yuki.vision.app.AppActivity
import com.yuki.vision.app.CATEGORY_DATA
import com.yuki.vision.app.goToVideoPlayer
import com.yuki.vision.module.discovery.category.CategoryResponse
import com.yuki.vision.module.discovery.category.detail.di.CategoryDetailModule
import com.yuki.vision.module.discovery.category.detail.di.DaggerCategoryDetailComponent
import com.yuki.vision.module.home.HomeResponse.Issue.Item
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.imageLoader.ImageLoader
import kotlinx.android.synthetic.main.activity_category_detail.*

/**
 * 项目：Vision
 * 作者：Yuki - 2018/1/31
 * 邮箱：125508663@qq.com
 */

class CategoryDetailActivity : AppActivity<CategoryDetailPresenter, CategoryDetailAdapter, Item>() {
	private lateinit var categoryData : CategoryResponse
	override fun initRecyclerView() : RecyclerView {
		return recyclerView
	}
	
	override fun requestData(isRefresh : Boolean) {
		when {
			isRefresh ->
				mPresenter?.getCategoryDetailList(categoryData.id)
			else ->
				mPresenter?.loadMoreData()
			
		}
	}
	
	override fun initActivityComponent(appComponent : AppComponent) {
		DaggerCategoryDetailComponent //如找不到该类,请编译一下项目
				.builder()
				.appComponent(appComponent)
				.categoryDetailModule(CategoryDetailModule(this))
				.build()
				.inject(this)
	}
	
	override fun initEvent() {
		super.initEvent()
		mAdapter.setOnItemClickListener { adapter, view, position ->
			goToVideoPlayer(this,view, mAdapter.getItem(position))
		}
	}
	
	override fun initView(savedInstanceState : Bundle?) {
		super.initView(savedInstanceState)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		toolbar.setNavigationOnClickListener { finish() }
		categoryData = intent.getSerializableExtra(CATEGORY_DATA) as CategoryResponse
		Logger.d(categoryData.headerImage)
		
		tv_category_desc.text = categoryData.description
		
		collapsing_toolbar_layout.title = categoryData.name
		collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE) //设置还没收缩时状态下字体颜色
		collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK) //设置收缩后Toolbar上字体的颜色
		ImageLoader.loadImage(imageView,categoryData.headerImage,R.color.light_transparent)
		mAdapter.emptyView = mLoadingView
		requestData(true)
	}
	
	public override fun initLayoutId() : Int {
		return R.layout.activity_category_detail
	}
	

}

