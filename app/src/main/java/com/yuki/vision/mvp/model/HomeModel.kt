package com.yuki.vision.mvp.model

import com.yuki.vision.app.net.AppApi
import com.yuki.vision.app.net.AppCache
import com.yuki.vision.mvp.model.entity.HomeResponse
import com.yuki.xndroid.base.mvp.XModel
import com.yuki.xndroid.repository.IRepositoryManager
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import javax.inject.Inject


class HomeModel @Inject
constructor(repositoryManager: IRepositoryManager) : XModel(repositoryManager) {
    fun initData(): Observable<HomeResponse> {
        return Observable.just(mRepositoryManager.http(AppApi::class.java).getFirstHomeData())
                .flatMap {
                    mRepositoryManager.cache(AppCache::class.java).getFirstHomeData(it)
                }

    }

    fun initData(url: String): Observable<HomeResponse> {
        return Observable.just(mRepositoryManager.http(AppApi::class.java).getMoreHomeData(url))
                .flatMap {
                    mRepositoryManager.cache(AppCache::class.java).getMoreHomeData(it, DynamicKey(url))
                }
    }

}
