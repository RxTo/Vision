package com.yuki.vision.module.video

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
class VideoModel @Inject
constructor(repositoryManager : IRepositoryManager) : XModel(repositoryManager) {
	fun requestRelatedData(id : Long) : Observable<Issue> {
		return Observable.just(mRepositoryManager.http(AppApi::class.java).getRelatedData(id))
				.flatMap {
					mRepositoryManager.cache(AppCache::class.java).getRelatedData(it, DynamicKey(id))
				}
	}
}
