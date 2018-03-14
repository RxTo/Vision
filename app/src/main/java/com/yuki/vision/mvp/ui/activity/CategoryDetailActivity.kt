package com.yuki.vision.mvp.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.yuki.vision.R
import com.yuki.vision.app.CATEGORY_DATA
import com.yuki.vision.app.adapter.recycler.SlimViewHolder
import com.yuki.vision.app.base.AppActivity
import com.yuki.vision.app.durationFormat
import com.yuki.vision.app.goToVideoPlayer
import com.yuki.vision.app.utils.StatusBarUtil
import com.yuki.vision.di.component.DaggerCategoryDetailComponent
import com.yuki.vision.di.module.CategoryDetailModule
import com.yuki.vision.mvp.model.entity.CategoryResponse
import com.yuki.vision.mvp.model.entity.HomeResponse.Issue.Item
import com.yuki.vision.mvp.presenter.CategoryDetailPresenter
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.imageLoader.ImageLoader
import kotlinx.android.synthetic.main.activity_category_detail.*

/**
 * 项目：Vision
 * 作者：Yuki - 2018/1/31
 * 邮箱：125508663@qq.com
 */

class CategoryDetailActivity : AppActivity<CategoryDetailPresenter, Item>() {
    private lateinit var categoryData: CategoryResponse
    override fun initRecyclerView(): RecyclerView {
        return recyclerView
    }

    override fun requestData(isRefresh: Boolean) {
        when {
            isRefresh ->
                mPresenter?.getCategoryDetailList(categoryData.id)
            else ->
                mPresenter?.loadMoreData()

        }
    }

    override fun initActivityComponent(appComponent: AppComponent) {
        DaggerCategoryDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .categoryDetailModule(CategoryDetailModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setSupportActionBar(toolbar)
        StatusBarUtil.darkMode(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        categoryData = intent.getSerializableExtra(CATEGORY_DATA) as CategoryResponse

        tv_category_desc.text = categoryData.description
        collapsing_toolbar_layout.title = categoryData.name
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE) //设置还没收缩时状态下字体颜色
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK) //设置收缩后Toolbar上字体的颜色
        ImageLoader.loadImage(imageView, categoryData.headerImage, R.color.light_transparent)

        adapter.register(R.layout.item_category_detail, { holder: SlimViewHolder, data: Item?, position: Int ->
            val itemData = data?.data
            val cover = itemData?.cover?.feed ?: ""
            // 加载封页图
            ImageLoader.loadImage(holder.find(R.id.iv_image), cover, R.mipmap.placeholder_banner)
            holder.text(R.id.tv_title, itemData?.title ?: "")

            // 格式化时间
            val timeFormat = durationFormat(itemData?.duration)

            holder.text(R.id.tv_tag, "${itemData?.category}/$timeFormat")
        })
                .layout(layoutManager)
                .empty(R.layout.view_loading)
                .click({ _, view, position ->
                    goToVideoPlayer(this, view, position as Item?)
                })
                .loadMore(loadMore, { requestData(false) })
        requestData(true)
    }

    public override fun initLayoutId(): Int {
        return R.layout.activity_category_detail
    }


}

