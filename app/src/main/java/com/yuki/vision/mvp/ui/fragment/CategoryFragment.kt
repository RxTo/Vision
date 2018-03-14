package com.yuki.vision.mvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.yuki.vision.R
import com.yuki.vision.app.CATEGORY_DATA
import com.yuki.vision.app.base.AppFragment
import com.yuki.vision.app.startActivity
import com.yuki.vision.di.component.DaggerCategoryComponent
import com.yuki.vision.di.module.CategoryModule
import com.yuki.vision.mvp.model.entity.CategoryResponse
import com.yuki.vision.mvp.presenter.CategoryPresenter
import com.yuki.vision.mvp.ui.activity.CategoryDetailActivity
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.imageLoader.ImageLoader
import kotlinx.android.synthetic.main.fragment_recyclerview.*


class CategoryFragment : AppFragment<CategoryPresenter, CategoryResponse>() {
    override fun initView(savedInstanceState: Bundle?) {
        adapter
                .layout(layoutManager)
                .register(R.layout.item_category, { holder, data, position ->
                    ImageLoader.loadImage(holder.find(R.id.iv_category), data?.bgPicture, R.color.light_transparent)
                    holder.text(R.id.tv_category_name, data?.name)
                    holder.itemView.setOnClickListener { goToActivity(it, position) }
                })
        requestData(true)
    }


    private fun goToActivity(view: View, position: Int) {
        context?.startActivity<CategoryDetailActivity>(CATEGORY_DATA to adapter.getItem(position))
    }

    override fun initRecyclerView(): RecyclerView {
        return recyclerView
    }


    override fun requestData(isRefresh: Boolean) {
        mPresenter?.initData()
    }

    override fun initFragmentComponent(appComponent: AppComponent) {
        DaggerCategoryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .categoryModule(CategoryModule(this))
                .build()
                .inject(this)
    }

    public override fun initLayoutId(): Int {
        return R.layout.fragment_recyclerview
    }


    companion object {
        fun newInstance(title: String): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            fragment.arguments = args
            args.putString("title", title)
            return fragment
        }
    }


}
