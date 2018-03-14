package com.yuki.vision.mvp.model

import com.yuki.vision.app.net.AppApi
import com.yuki.vision.app.net.AppCache
import com.yuki.vision.mvp.model.entity.TabInfoResponse
import com.yuki.xndroid.base.mvp.XModel
import com.yuki.xndroid.di.scope.FragmentScope
import com.yuki.xndroid.repository.IRepositoryManager
import io.reactivex.Observable
import javax.inject.Inject


@FragmentScope
class HotModel @Inject
constructor(repositoryManager: IRepositoryManager) : XModel(repositoryManager) {
    fun getTabInfo(): Observable<TabInfoResponse> {
        return Observable.just(mRepositoryManager.http(AppApi::class.java).getRankList())
                .flatMap {
                    mRepositoryManager.cache(AppCache::class.java).getRankList(it)
                }
    }
}
