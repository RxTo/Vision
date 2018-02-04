package com.yuki.vision.module.hot.rank

import com.yuki.vision.app.AppApi
import com.yuki.vision.app.AppCache
import com.yuki.vision.module.home.HomeResponse.Issue
import com.yuki.xndroid.base.mvp.XModel
import com.yuki.xndroid.di.scope.FragmentScope
import com.yuki.xndroid.repository.IRepositoryManager
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import javax.inject.Inject


@FragmentScope
class RankModel @Inject
constructor(repositoryManager : IRepositoryManager) : XModel(repositoryManager) {
	fun requestRankList(apiUrl : String) : Observable<Issue> {
		return Observable.just(mRepositoryManager.http(AppApi::class.java).getIssueData(apiUrl))
				.flatMap {
					mRepositoryManager.cache(AppCache::class.java).getIssueData(it, DynamicKey(apiUrl))
				}
		
	}
}