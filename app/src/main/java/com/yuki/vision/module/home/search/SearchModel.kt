package com.yuki.vision.module.home.search

import com.yuki.vision.app.AppApi
import com.yuki.vision.app.AppCache
import com.yuki.vision.module.home.HomeResponse
import com.yuki.xndroid.base.mvp.XModel
import com.yuki.xndroid.repository.IRepositoryManager
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import javax.inject.Inject

/**
 * 项目：Vision
 * 作者：Yuki - 2018/1/28
 * 邮箱：125508663@qq.com
 **/

class SearchModel @Inject constructor(repositoryManager : IRepositoryManager) : XModel(repositoryManager) {
	
	
	/**
	 * 请求热门关键词的数据
	 */
	fun getHotWord() : Observable<ArrayList<String>> {
		return Observable.just(mRepositoryManager.http(AppApi::class.java).getHotWord())
				.flatMap {
					mRepositoryManager.cache(AppCache::class.java).getHotWord(it)
				}
	}
	
	
	/**
	 * 搜索关键词返回的结果
	 */
	fun getSearchResult(words : String) : Observable<HomeResponse.Issue> {
		return Observable.just(mRepositoryManager.http(AppApi::class.java).getSearchData(words))
				.flatMap {
					mRepositoryManager.cache(AppCache::class.java).getSearchData(it, DynamicKey(words))
				}
	}
	
	/**
	 * 加载更多数据
	 */
	fun getMoreIssue(url : String) : Observable<HomeResponse.Issue> {
		return Observable.just(mRepositoryManager.http(AppApi::class.java).getMoreIssue(url))
				.flatMap {
					mRepositoryManager.cache(AppCache::class.java).getSearchData(it, DynamicKey(url))
				}
	}
}