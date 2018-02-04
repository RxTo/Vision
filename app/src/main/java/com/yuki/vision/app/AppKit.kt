package com.yuki.vision.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.trello.rxlifecycle2.LifecycleProvider
import com.yuki.vision.R
import com.yuki.vision.module.home.HomeResponse.Issue.Item
import com.yuki.vision.module.video.VideoActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable

/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/23
 * 邮箱：125508663@qq.com
 **/


val VIDEO_DATA = "video_data"
val CATEGORY_DATA = "category_data"
val IMG_TRANSITION = "IMG_TRANSITION"
val TRANSITION = "TRANSITION"

//sp 存储的文件名
val FILE_WATCH_HISTORY_NAME = "watch_history_file"   //观看记录

val FILE_COLLECTION_NAME = "collection_file"    //收藏视屏缓存的文件名


/**
 * 跳转到视频详情页面播放
 * @param view
 */
fun goToVideoPlayer(activity : Activity, view : View, itemData : Item?) {
	val intent = Intent(activity, VideoActivity::class.java)
	intent.putExtra(VIDEO_DATA, itemData)
	intent.putExtra(TRANSITION, true)
	if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
		val pair = android.support.v4.util.Pair(view, IMG_TRANSITION)
		activity.let {
			val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(it, pair)
			it.startActivity(intent, activityOptions.toBundle())
		}
	} else {
		activity.startActivity(intent)
		activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
	}
}


//线程切换
fun <T> Observable<T>.applySchedulers(mView : LifecycleProvider<*>) : Observable<T> {
	return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(mView.bindToLifecycle())
	
}

fun durationFormat(duration : Long?) : String {
	val minute = duration!! / 60
	val second = duration % 60
	return if (minute <= 9) {
		if (second <= 9) {
			"0$minute' 0$second''"
		} else {
			"0$minute' $second''"
		}
	} else {
		if (second <= 9) {
			"$minute' 0$second''"
		} else {
			"$minute' $second''"
		}
	}
}

fun View.snackBar(msg : String, duration : Int = Toast.LENGTH_SHORT) {
	Snackbar.make(this, msg, duration).show()
}

fun Activity.toast(msg : String, duration : Int = Toast.LENGTH_SHORT) {
	Toast.makeText(this, msg, duration).show()
}

inline fun <reified Y : Activity> Context.startActivity() {
	this.startActivity(Intent(this, Y::class.java))
}

inline fun <reified Y : Activity> Context.startActivity(vararg pairs : Pair<String, Serializable?>) {
	val intent = Intent(this, Y::class.java)
	pairs.forEach {
		intent.putExtra(it.first, it.second)
	}
	this.startActivity(intent)
}


inline fun <reified Y : Activity> Context.startActivity(options : ActivityOptionsCompat?) {
	this.startActivity(Intent(this, Y::class.java), options?.toBundle())
}

fun Fragment.toast(msg : String, duration : Int = Toast.LENGTH_SHORT) {
	Toast.makeText(this.context, msg, duration).show()
}