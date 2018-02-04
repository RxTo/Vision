package com.yuki.vision.module.video

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.transition.Transition
import android.widget.ImageView
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.yuki.vision.R
import com.yuki.vision.app.AppActivity
import com.yuki.vision.app.IMG_TRANSITION
import com.yuki.vision.app.VIDEO_DATA
import com.yuki.vision.module.home.HomeResponse.Issue.Item
import com.yuki.vision.module.video.di.DaggerVideoComponent
import com.yuki.vision.module.video.di.VideoModule
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.imageLoader.GlideApp
import com.yuki.xndroid.imageLoader.ImageLoader
import kotlinx.android.synthetic.main.activity_video.*

@SuppressLint("SimpleDateFormat")
class VideoActivity : AppActivity<VideoPresenter, VideoAdapter, Item>() {
	
	override fun initRecyclerView() : RecyclerView = recyclerView
	
	override fun requestData(isRefresh : Boolean) {
		mPresenter?.loadVideoInfo(itemData)
	}
	
	private lateinit var itemData : Item
	
	private lateinit var transition : Transition
	
	override fun initLayoutId() : Int {
		return R.layout.activity_video
	}
	
	fun initVideoUrl(url : String) {
	
	}
	
	override fun initActivityComponent(appComponent : AppComponent?) {
		DaggerVideoComponent.builder()
				.appComponent(appComponent)
				.videoModule(VideoModule(this))
				.build()
				.inject(this)
	}
	
	override fun initView(savedInstanceState : Bundle?) {
		super.initView(savedInstanceState)
		itemData = intent.getSerializableExtra(VIDEO_DATA) as Item
		//过渡动画
		initTransition()
		initVideoViewConfig()
		mPresenter?.loadVideoInfo(itemData)
		GlideApp.with(this)
				.load(itemData.data?.cover?.blurred)
				.centerCrop()
				.format(DecodeFormat.PREFER_ARGB_8888)
				.transition(DrawableTransitionOptions().crossFade())
				.into(recyclerViewBg)
		mAdapter.setEnableLoadMore(false)
		requestData(true)
	}
	
	override fun initEvent() {
		super.initEvent()
		mAdapter.setOnItemClickListener({ adapter, view, position ->
			mPresenter?.loadVideoInfo(mAdapter.getItem(position))
		})
	}
	

	
	/**
	 * 初始化 VideoView 的配置
	 */
	private fun initVideoViewConfig() {
		saveHistoryInfo(itemData)
		//增加封面
		val imageView = ImageView(this)
		imageView.scaleType = ImageView.ScaleType.CENTER_CROP
		ImageLoader.loadImage(imageView, itemData.data?.cover?.feed)
		video.thumbImageView = imageView
	}
	
	
	/**
	 * 1.加载视频信息
	 */
	fun loadVideoInfo() {
		mPresenter?.loadVideoInfo(itemData)
	}
	
	private fun initTransition() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			postponeEnterTransition()
			ViewCompat.setTransitionName(video, IMG_TRANSITION)
			addTransitionListener()
			startPostponedEnterTransition()
		} else {
			loadVideoInfo()
		}
	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private fun addTransitionListener() {
		transition = window.sharedElementEnterTransition
		transition.addListener(object : Transition.TransitionListener {
			override fun onTransitionResume(p0 : Transition?) {
			}
			
			override fun onTransitionPause(p0 : Transition?) {
			}
			
			override fun onTransitionCancel(p0 : Transition?) {
			}
			
			override fun onTransitionStart(p0 : Transition?) {
			}
			
			override fun onTransitionEnd(p0 : Transition?) {
				loadVideoInfo()
				transition.removeListener(this)
			}
			
		})
	}
	
	
	override fun useEventBus() : Boolean {
		return false
	}
	
	/**
	 * 保存观看记录
	 */
	private fun saveHistoryInfo(watchItem : Item) {
	
	}
	
	
}
