package com.yuki.weishi.widget

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import com.yuki.vision.R


/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/16
 * 邮箱：125508663@qq.com
 **/

class SmartImageView @JvmOverloads constructor(context : Context, attrs : AttributeSet? = null, defStyleAttr : Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {
	
	private var ratio = -1f
	
	
	fun initSize(originalWidth : Int, originalHeight : Int) {
		ratio = originalWidth.toFloat() / originalHeight
	}
	
	override fun onMeasure(widthMeasureSpec : Int, heightMeasureSpec : Int) {
		if (ratio > 0) {
			val width = View.MeasureSpec.getSize(widthMeasureSpec)
			var height = View.MeasureSpec.getSize(heightMeasureSpec)
			if (width > 0) {
				height = (width.toFloat() / ratio).toInt()
			}
			setMeasuredDimension(width, height)
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec)
		}
	}
	
	init {
		val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmartImageView)
		ratio = typedArray.getFloat(R.styleable.SmartImageView_ratio, -1f)
		typedArray.recycle()
	}
}
	

