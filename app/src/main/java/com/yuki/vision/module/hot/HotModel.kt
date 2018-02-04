package com.yuki.vision.module.hot

import com.yuki.vision.app.AppApi
import com.yuki.vision.app.AppCache
import com.yuki.xndroid.base.mvp.XModel
import com.yuki.xndroid.di.scope.FragmentScope
import com.yuki.xndroid.repository.IRepositoryManager
import io.reactivex.Observable
import javax.inject.Inject


@FragmentScope
class HotModel @Inject
constructor(repositoryManager : IRepositoryManager) : XModel(repositoryManager) {
	fun getTabInfo() : Observable<TabInfoResponse> {
		return Observable.just(mRepositoryManager.http(AppApi::class.java).getRankList())
				.flatMap {
					mRepositoryManager.cache(AppCache::class.java).getRankList(it)
				}
	}
}
