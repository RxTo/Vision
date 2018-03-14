package com.yuki.vision.mvp.model

import com.yuki.vision.app.net.AppApi
import com.yuki.vision.app.net.AppCache
import com.yuki.vision.mvp.model.entity.HomeResponse.Issue
import com.yuki.xndroid.base.mvp.XModel
import com.yuki.xndroid.di.scope.ActivityScope
import com.yuki.xndroid.repository.IRepositoryManager
import io.reactivex.Observable
import io.rx_cache2.DynamicKey

import javax.inject.Inject


@ActivityScope
class VideoModel @Inject
constructor(repositoryManager: IRepositoryManager) : XModel(repositoryManager) {
    fun requestRelatedData(id: Long): Observable<Issue> {
        return Observable.just(mRepositoryManager.http(AppApi::class.java).getRelatedData(id))
                .flatMap {
                    mRepositoryManager.cache(AppCache::class.java).getRelatedData(it, DynamicKey(id))
                }
    }
}
