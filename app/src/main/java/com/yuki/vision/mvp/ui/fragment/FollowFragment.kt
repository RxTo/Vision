package com.yuki.vision.mvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.yuki.vision.R
import com.yuki.vision.app.adapter.recycler.SlimAdapter
import com.yuki.vision.app.adapter.recycler.SlimViewHolder
import com.yuki.vision.app.base.AppFragment
import com.yuki.vision.app.durationFormat
import com.yuki.vision.app.goToVideoPlayer
import com.yuki.vision.di.component.DaggerFollowComponent
import com.yuki.vision.di.module.FollowModule
import com.yuki.vision.mvp.model.entity.HomeResponse.Issue.Item
import com.yuki.vision.mvp.presenter.FollowPresenter
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.imageLoader.ImageLoader
import kotlinx.android.synthetic.main.fragment_recyclerview.*


class FollowFragment : AppFragment<FollowPresenter, Item>() {


    override fun initView(savedInstanceState: Bundle?) {
        adapter.layout(layoutManager)
                .register(R.layout.item_follow, { holder, data, _ ->
                    when {
                        data?.type == "videoCollectionWithBrief" -> setAuthorInfo(data, holder)
                        else ->
                            throw IllegalAccessException("Api 解析出错了，出现其他类型")
                    }
                })
                .empty(R.layout.view_loading)
                .loadMore(loadMore, {
                    requestData(false)
                })

        requestData(true)
    }

    /**
     * 加载作者信息
     */
    private fun setAuthorInfo(item: Item?, data: SlimViewHolder) {
        val headerData = item?.data?.header
        /**
         * 加载作者头像
         */
        ImageLoader.loadRoundImage(data.find(R.id.iv_avatar), headerData?.icon, R.mipmap.default_avatar)
        data.text(R.id.tv_title, headerData?.title)
                .text(R.id.tv_desc, headerData?.description)
        val recyclerView = data.find<RecyclerView>(R.id.fl_recyclerView)
        /**
         * 设置嵌套水平的 RecyclerView
         */
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        SlimAdapter.create<Item>(recyclerView)
                .register(R.layout.item_follow_horizontal, { holder, item, position ->
                    val itemData = item?.data
                    ImageLoader.loadImage(holder.find(R.id.iv_cover_feed), itemData?.cover?.feed, R.mipmap.bg_loading, R.mipmap.bg_load_error)
                    //横向 RecyclerView 封页图下面标题
                    holder.text(R.id.tv_title, itemData?.title ?: "")

                    // 格式化时间
                    val timeFormat = durationFormat(itemData?.duration)
                    //标签
                    holder.run {
                        if (itemData?.tags != null && itemData.tags.size > 0) {
                            text(R.id.tv_tag, "${itemData.tags[0].name} / $timeFormat")
                        } else {
                            text(R.id.tv_tag, timeFormat)
                        }
                    }
                })
                .initNew(item?.data?.itemList!!)
                .click { slimAdapter, view, position ->
                    goToVideoPlayer(_mActivity, view, position as Item?)
                }

    }

    override fun initRecyclerView(): RecyclerView {
        return recyclerView
    }

    override fun requestData(isRefresh: Boolean) {
        mPresenter?.initData(isRefresh)
    }


    override fun initFragmentComponent(appComponent: AppComponent) {
        DaggerFollowComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .followModule(FollowModule(this))
                .build()
                .inject(this)
    }

    public override fun initLayoutId(): Int {
        return R.layout.fragment_recyclerview
    }

    companion object {
        fun newInstance(title: String): FollowFragment {
            val fragment = FollowFragment()
            val args = Bundle()
            fragment.arguments = args
            args.putString("title", title)
            return fragment
        }
    }

}
