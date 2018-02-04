package com.yuki.vision.module.discovery

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * 项目：Vision
 * 作者：Yuki - 2018/1/31
 * 邮箱：125508663@qq.com
 **/

class ViewPagerAdapter : FragmentPagerAdapter {
	
	private var titles : List<String>? = null
	private val data : List<Fragment>
	
	constructor(data : List<Fragment>, fragmentManager : FragmentManager) : super(fragmentManager) {
		this.data = data
	}
	
	constructor(data : List<Fragment>, titles : List<String>, fragmentManager : FragmentManager) : super(fragmentManager) {
		this.data = data
		this.titles = titles
		
	}
	
	override fun getItem(position : Int) : Fragment {
		return data[position]
	}
	
	override fun getPageTitle(position : Int) : CharSequence? {
		return titles?.get(position) ?: data[position].arguments?.getString("title")
		
	}
	
	override fun getCount() : Int {
		return data.size
	}
}