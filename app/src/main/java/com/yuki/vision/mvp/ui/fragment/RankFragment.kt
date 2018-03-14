package com.yuki.vision.mvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.yuki.vision.R
import com.yuki.vision.app.adapter.recycler.SlimViewHolder
import com.yuki.vision.app.base.AppFragment
import com.yuki.vision.app.durationFormat
import com.yuki.vision.app.goToVideoPlayer
import com.yuki.vision.di.component.DaggerRankComponent
import com.yuki.vision.di.module.RankModule
import com.yuki.vision.mvp.model.entity.HomeResponse.Issue.Item
import com.yuki.vision.mvp.presenter.RankPresenter
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.imageLoader.ImageLoader
import kotlinx.android.synthetic.main.fragment_recyclerview.*


class RankFragment : AppFragment<RankPresenter, Item>() {

    override fun initView(savedInstanceState: Bundle?) {
        adapter
                .register(R.layout.item_category_detail, { holder: SlimViewHolder, data: Item?, _: Int ->
                    val itemData = data?.data
                    val cover = itemData?.cover?.feed ?: ""
                    // 加载封页图
                    ImageLoader.loadImage(holder.find(R.id.iv_image), cover, R.mipmap.placeholder_banner)
                    holder.text(R.id.tv_title, itemData?.title ?: "")

                    // 格式化时间
                    val timeFormat = durationFormat(itemData?.duration)

                    holder.text(R.id.tv_tag, "${itemData?.category}/$timeFormat")
                })
                .click({ holder, view, item ->
                    goToVideoPlayer(_mActivity, view, item as Item?)
                })
                .layout(layoutManager)
        requestData(true)
    }

    override fun initRecyclerView(): RecyclerView {
        return recyclerView
    }


    override fun requestData(isRefresh: Boolean) {
        mPresenter?.requestRankList(arguments?.getString("url").toString(), isRefresh)
    }

    override fun initFragmentComponent(appComponent: AppComponent) {
        DaggerRankComponent //如找不到该类,请编译一下项目
                .builder().appComponent(appComponent).rankModule(RankModule(this)).build().inject(this)
    }

    public override fun initLayoutId(): Int {
        return R.layout.fragment_recyclerview
    }

    companion object {
        fun newInstance(url: String): RankFragment {
            val fragment = RankFragment()
            val args = Bundle()
            fragment.arguments = args
            args.putString("url", url)
            return fragment
        }
    }


}
