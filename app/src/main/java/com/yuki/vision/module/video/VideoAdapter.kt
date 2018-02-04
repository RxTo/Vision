package com.yuki.vision.module.video

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuki.vision.R
import com.yuki.vision.app.durationFormat
import com.yuki.vision.module.home.HomeResponse.Issue.Item
import com.yuki.vision.module.home.HomeResponse.Issue.Item.Companion.TEXT_CARD
import com.yuki.vision.module.home.HomeResponse.Issue.Item.Companion.TYPE_TEXT_HEAD
import com.yuki.vision.module.home.HomeResponse.Issue.Item.Companion.VIDEO_CARD
import com.yuki.xndroid.imageLoader.ImageLoader

/**
 * 项目：Vision
 * 作者：Yuki - 2018/1/30
 * 邮箱：125508663@qq.com
 **/

class VideoAdapter : BaseMultiItemQuickAdapter<Item, BaseViewHolder>(null) {
	
	
	init {
		addItemType(TEXT_CARD, R.layout.item_video_text_card)
		addItemType(VIDEO_CARD, R.layout.item_video_small_card)
		addItemType(TYPE_TEXT_HEAD,R.layout.item_video_detail_info)
	}
	
	@SuppressLint("SetTextI18n")
	private fun initHeadView(data : Item?, helper : BaseViewHolder) {
		helper.run {
			setText(R.id.tv_title, data?.data?.title)
			//视频简介
			setText(R.id.tvSynopsis, data?.data?.description)
			//标签
			setText(R.id.tv_tag, "#${data?.data?.category} / ${durationFormat(data?.data?.duration)}")
			//喜欢
			setText(R.id.tv_action_favorites, data?.data?.consumption?.collectionCount.toString())
			//分享
			setText(R.id.tv_action_share, data?.data?.consumption?.shareCount.toString())
			//评论
			setText(R.id.tv_action_reply, data?.data?.consumption?.replyCount.toString())
		}
		data?.data?.author?.let {
			helper.run {
				setText(R.id.tv_author_name, data.data.author.name)
				setText(R.id.tv_author_desc, data.data.author.description)
				ImageLoader.loadRoundImage(getView(R.id.iv_avatar), it.icon, R.mipmap.default_avatar)
			}
		} ?: helper.setVisible(R.id.layout_author_view, false)
		
	}
	
	override fun convert(helper : BaseViewHolder, item : Item?) {
		when (helper.itemViewType) {
			TYPE_TEXT_HEAD -> initHeadView(item, helper)
			TEXT_CARD -> {
				helper.setText(R.id.tv_text_card, item?.data?.text)
			}
			else ->
				helper.run {
					setText(R.id.tv_title, item?.data?.title)
					setText(R.id.tv_tag, "${item?.data?.category} / ${durationFormat(item?.data?.duration)}")
					ImageLoader.loadImage(getView(R.id.iv_video_small_card), item?.data?.cover?.detail, R.mipmap.placeholder_banner)
				}
			
		}
	}
}