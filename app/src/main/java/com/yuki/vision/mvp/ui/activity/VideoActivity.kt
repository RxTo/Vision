package com.yuki.vision.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import cn.jzvd.JZVideoPlayer
import com.yuki.vision.R
import com.yuki.vision.app.VIDEO_DATA
import com.yuki.vision.app.adapter.recycler.SlimViewHolder
import com.yuki.vision.app.base.AppActivity
import com.yuki.vision.app.durationFormat
import com.yuki.vision.app.utils.StatusBarUtil
import com.yuki.vision.di.component.DaggerVideoComponent
import com.yuki.vision.di.module.VideoModule
import com.yuki.vision.mvp.model.entity.HomeResponse.Issue.Item
import com.yuki.vision.mvp.presenter.VideoPresenter
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.imageLoader.ImageLoader
import kotlinx.android.synthetic.main.activity_video.*


class VideoActivity : AppActivity<VideoPresenter, Item>() {

    private lateinit var itemData: Item
    override fun initRecyclerView(): RecyclerView {
        return recyclerView
    }

    override fun initLayoutId(): Int {
        return R.layout.activity_video
    }

    override fun initActivityComponent(appComponent: AppComponent?) {
        super.initActivityComponent(appComponent)
        DaggerVideoComponent.builder().appComponent(appComponent)
                .videoModule(VideoModule(this))
                .build().inject(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        StatusBarUtil.darkMode(this)
        itemData = intent.getSerializableExtra(VIDEO_DATA) as Item
        ImageLoader.loadImage(recyclerViewBg, itemData.data?.cover?.blurred)
        ImageLoader.loadImage(video.thumbImageView, itemData.data?.cover?.feed)
        adapter
                .layout(layoutManager)
                .multiple({ item, _ ->
                    when (item.getItemType()) {
                        Item.TEXT_CARD -> R.layout.item_video_text_card
                        Item.VIDEO_CARD -> R.layout.item_video_small_card
                        Item.TYPE_TEXT_HEAD -> R.layout.item_video_detail_info
                        else -> 0
                    }
                })
                .register(R.layout.item_video_text_card, { holder, item, position ->
                    holder.text(R.id.tv_text_card, item?.data?.text)
                })
                .register(R.layout.item_video_small_card, { holder, item, position ->
                    holder.run {
                        text(R.id.tv_title, item?.data?.title)
                        text(R.id.tv_tag, "${item?.data?.category} / ${durationFormat(item?.data?.duration)}")
                        ImageLoader.loadImage(holder.find(R.id.iv_video_small_card), item?.data?.cover?.detail, R.mipmap.placeholder_banner)
                    }
                })
                .register(R.layout.item_video_detail_info, { holder, item, position ->
                    initHeadView(item, holder)
                })
                .empty(mLoadingView)
                .click({ holder, view, item ->
                    JZVideoPlayer.releaseAllVideos()
                    mPresenter?.loadVideoInfo(item as Item?)
                })
        mPresenter?.loadVideoInfo(itemData)
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        video.widthRatio = 16
        video.heightRatio = 9
    }

    override fun requestData(isRefresh: Boolean) {
        //没有刷新功能
    }

    fun initVideoUrl(data: Item?) {
        adapter.initNew(listOf(data))
        ImageLoader.loadImage(video.thumbImageView, data?.data?.cover?.feed)
        video.setUp(data?.data?.playUrl, JZVideoPlayer.SCREEN_WINDOW_NORMAL)
    }

    override fun onBackPressedSupport() {
        if (JZVideoPlayer.backPress()) {
            return
        }
        super.onBackPressedSupport()
    }

    override fun onPause() {
        super.onPause()
        JZVideoPlayer.goOnPlayOnPause()
    }

    override fun onResume() {
        super.onResume()
        JZVideoPlayer.goOnPlayOnResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        JZVideoPlayer.releaseAllVideos()
    }

    @SuppressLint("textI18n")
    private fun initHeadView(data: Item?, holder: SlimViewHolder) {
        holder.run {
            text(R.id.tv_title, data?.data?.title)
            //视频简介
            text(R.id.tvSynopsis, data?.data?.description)
            //标签
            text(R.id.tv_tag, "#${data?.data?.category} / ${durationFormat(data?.data?.duration)}")
            //喜欢
            text(R.id.tv_action_favorites, data?.data?.consumption?.collectionCount.toString())
            //分享
            text(R.id.tv_action_share, data?.data?.consumption?.shareCount.toString())
            //评论
            text(R.id.tv_action_reply, data?.data?.consumption?.replyCount.toString())
        }
        data?.data?.author?.let {
            holder.run {
                text(R.id.tv_author_name, data.data.author.name)
                text(R.id.tv_author_desc, data.data.author.description)
                ImageLoader.loadRoundImage(find(R.id.iv_avatar), it.icon, R.mipmap.default_avatar)
            }
        } ?: holder.visibility(R.id.layout_author_view, false)

    }
}
