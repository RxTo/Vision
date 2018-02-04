package com.yuki.vision.module.discovery.category

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.yuki.vision.R
import com.yuki.vision.app.AppFragment
import com.yuki.vision.app.CATEGORY_DATA
import com.yuki.vision.app.startActivity
import com.yuki.vision.module.discovery.category.detail.CategoryDetailActivity
import com.yuki.vision.module.discovery.category.di.CategoryModule
import com.yuki.vision.module.discovery.category.di.DaggerCategoryComponent
import com.yuki.xndroid.di.component.AppComponent
import kotlinx.android.synthetic.main.fragment_recyclerview.*


class CategoryFragment : AppFragment<CategoryPresenter, CategoryAdapter, CategoryResponse>() {
	
	override fun goToActivity(view:View,position : Int) {
		context?.startActivity<CategoryDetailActivity>(CATEGORY_DATA to mAdapter.getItem(position))
		
	}
	
	override fun initRecyclerView() : RecyclerView {
		return recyclerView
	}
	
	override fun requestData(isRefresh : Boolean) {
		mPresenter?.initData()
	}
	
	override fun useLoadMore() : Boolean {
		return false
	}
	
	
	override fun initFragmentComponent(appComponent : AppComponent) {
		DaggerCategoryComponent //如找不到该类,请编译一下项目
				.builder()
				.appComponent(appComponent)
				.categoryModule(CategoryModule(this))
				.build()
				.inject(this)
	}
	
	public override fun initLayoutId() : Int {
		return R.layout.fragment_recyclerview
	}
	
	
	companion object {
		fun newInstance(title : String) : CategoryFragment {
			val fragment = CategoryFragment()
			val args = Bundle()
			fragment.arguments = args
			args.putString("title", title)
			return fragment
		}
	}
	
	
}
