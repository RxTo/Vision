package com.yuki.vision

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.orhanobut.logger.Logger
import com.yuki.vision.MainActivity
import com.yuki.vision.R
import com.yuki.vision.app.startActivity
import com.yuki.xndroid.base.XActivity
import com.yuki.xndroid.base.mvp.IPresenter
import com.yuki.xndroid.imageLoader.ImageLoader
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_splash.*
import me.weyye.hipermission.HiPermission
import me.weyye.hipermission.PermissionCallback
import me.weyye.hipermission.PermissionItem
import java.util.concurrent.TimeUnit


/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/9
 * 邮箱：125508663@qq.com
 */
class SplashActivity : XActivity<IPresenter>() {
	private val picUrl = "http://joymepic.joyme.com/article/uploads/allimg/201510/1446110515207331.jpg"
	override fun initView(savedInstanceState : Bundle?) {
		checkPermission()
	}
	
	override fun initEvent() {
		tvSkip.setOnClickListener { toMain() }
	}
	
	@SuppressLint("SetTextI18n")
	private fun countDown() {
		Observable.interval(1, TimeUnit.SECONDS)
				.compose(bindToLifecycle())
				.map { 2 - it }
				.take(3)
				.observeOn(AndroidSchedulers.mainThread())
				.doOnSubscribe { tvSkip.visibility = View.VISIBLE }
				.doOnComplete { toMain() }
				.subscribe({
					tvSkip.text = "跳过广告 $it"
				})
	}
	
	private fun toMain() {
		startActivity<MainActivity>()
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
		finish()
	}
	
	override fun initLayoutId() : Int {
		return R.layout.activity_splash
	}
	
	/**
	 * 6.0以下版本(系统自动申请) 不会弹框
	 * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
	 */
	private fun checkPermission() {
		val permissionItems = ArrayList<PermissionItem>()
		permissionItems.add(PermissionItem(Manifest.permission.READ_PHONE_STATE, "手机状态", R.drawable.permission_ic_phone))
		permissionItems.add(PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储空间", R.drawable.permission_ic_storage))
		HiPermission.create(this)
				.title("亲爱的上帝")
				.msg("为了能够正常使用，请开启这些权限吧！")
				.permissions(permissionItems)
				.style(R.style.PermissionDefaultBlueStyle)
				.animStyle(R.style.PermissionAnimScale)
				.checkMutiPermission(object : PermissionCallback {
					override fun onClose() {
						initAd()
					}
					
					override fun onFinish() {
						initAd()
						
					}
					
					override fun onDeny(permission : String, position : Int) {
						Logger.i("permission_onDeny")
					}
					
					override fun onGuarantee(permission : String, position : Int) {
						Logger.i("permission_onGuarantee")
					}
				})
	}
	
	private fun initAd() {
		ImageLoader.loadImage(ivAd, picUrl, 0, 0, object : RequestListener<Drawable> {
			override fun onResourceReady(resource : Drawable?, model : Any?, target : Target<Drawable>?, dataSource : DataSource?, isFirstResource : Boolean) : Boolean {
				countDown()
				return false
			}
			
			override fun onLoadFailed(e : GlideException?, model : Any?, target : Target<Drawable>?, isFirstResource : Boolean) : Boolean {
				toMain()
				return false
			}
		})
	}
}
