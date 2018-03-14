package com.yuki.vision.mvp.ui.activity

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.yuki.vision.R
import com.yuki.vision.app.startActivity
import com.yuki.xndroid.base.XActivity
import com.yuki.xndroid.base.mvp.IPresenter
import com.yuki.xndroid.imageLoader.ImageLoader
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit


/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/9
 * 邮箱：125508663@qq.com
 */
class SplashActivity : XActivity<IPresenter>() {
    private val picUrl = "https://pic3.zhimg.com/v2-5af460972557190bd4306ad66f360d4a.jpg"
    override fun initView(savedInstanceState: Bundle?) {
        initAd()
    }

    override fun initEvent() {
        tvSkip.setOnClickListener { toMain() }
    }

    @SuppressLint("SetTextI18n")
    private fun countDown() {
        Observable.interval(1, TimeUnit.SECONDS)
                .map { 2 - it }
                .take(3)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { tvSkip.visibility = View.VISIBLE }
                .doOnComplete { toMain() }
                .compose(bindToLifecycle())
                .subscribe({
                    tvSkip.text = "跳过广告 $it"
                })
    }

    private fun toMain() {
        startActivity<MainActivity>()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    override fun initLayoutId(): Int {
        return R.layout.activity_splash
    }


    private fun initAd() {
        ImageLoader.loadImage(ivAd, picUrl, 0, 0, object : RequestListener<Drawable> {
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                countDown()
                return false
            }

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                toMain()
                return false
            }
        })
    }
}
