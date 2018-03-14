package com.yuki.vision.mvp.model

import com.yuki.vision.app.net.AppApi
import com.yuki.vision.app.net.AppCache
import com.yuki.vision.mvp.model.entity.HomeResponse.Issue
import com.yuki.xndroid.base.mvp.XModel
import com.yuki.xndroid.di.scope.FragmentScope
import com.yuki.xndroid.repository.IRepositoryManager
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import javax.inject.Inject


@FragmentScope
class FollowModel @Inject
constructor(repositoryManager: IRepositoryManager) : XModel(repositoryManager) {
    fun initData(): Observable<Issue> {
        return Observable.just(mRepositoryManager.http(AppApi::class.java).getFollowInfo())
                .flatMap {
                    mRepositoryManager.cache(AppCache::class.java).getFollowInfo(it)
                }

    }

    fun initData(url: String): Observable<Issue> {
        return Observable.just(mRepositoryManager.http(AppApi::class.java).getIssueData(url))
                .flatMap {
                    mRepositoryManager.cache(AppCache::class.java).getIssueData(it, DynamicKey(url))
                }
    }
}
