package com.yuki.vision.app

import android.content.Context
import android.net.ParseException
import com.google.gson.JsonParseException
import com.orhanobut.logger.Logger
import com.yuki.xndroid.kit.RxErrorListener
import com.yuki.xndroid.utils.DeviceUtils
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class AppRxErrorListener(private val context : Context) : RxErrorListener {
	
	override fun handleRxError(t : Throwable) : String {
		Logger.d(t)
		t.printStackTrace()
		//这里不光是只能打印错误,还可以根据不同的错误作出不同的逻辑处理
		var msg = "未知错误"
		if (t is UnknownHostException) {
			msg = "网络不可用"
		} else if (t is SocketTimeoutException) {
			msg = "请求网络超时"
		} else if (t is HttpException) {
			msg = convertStatusCode(t)
		} else if (t is JsonParseException || t is ParseException || t is JSONException) {
			msg = "数据解析错误"
		} else if (!DeviceUtils.netIsConnected(context))
			msg = "网络不可用"
		return msg
	}
	
	private fun convertStatusCode(httpException : HttpException) : String {
		return when {
			httpException.code() == 500 -> "服务器发生错误"
			httpException.code() == 404 -> "请求地址不存在"
			httpException.code() == 403 -> "请求被服务器拒绝"
			httpException.code() == 307 -> "请求被重定向到其他页面"
			else -> httpException.message()
		}
	}
	
	
}
