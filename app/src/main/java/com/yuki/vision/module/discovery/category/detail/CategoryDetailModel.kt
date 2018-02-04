package com.yuki.vision.module.discovery.category.detail

import com.yuki.vision.app.AppApi
import com.yuki.vision.app.AppCache
import com.yuki.vision.module.home.HomeResponse.Issue
import com.yuki.xndroid.base.mvp.XModel
import com.yuki.xndroid.di.scope.ActivityScope
import com.yuki.xndroid.repository.IRepositoryManager
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import javax.inject.Inject


@ActivityScope
class CategoryDetailModel @Inject
constructor(repositoryManager : IRepositoryManager) : XModel(repositoryManager) {
	/**
	 * 获取分类下的 List 数据
	 */
	fun getCategoryDetailList(id : Long) : Observable<Issue> {
		return Observable.just(mRepositoryManager.http(AppApi::class.java).getCategoryDetailList(id))
				.flatMap {
					mRepositoryManager.cache(AppCache::class.java).getCategoryDetailList(it, DynamicKey(id))
				}
	}
	
	/**
	 * 加载更多数据
	 */
	fun loadMoreData(url : String) : Observable<Issue> {
		return Observable.just(mRepositoryManager.http(AppApi::class.java).getIssueData(url))
				.flatMap {
					mRepositoryManager.cache(AppCache::class.java).getIssueData(it, DynamicKey(url))
				}
	}
}
