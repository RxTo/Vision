package com.yuki.vision.mvp.model

import com.yuki.vision.app.net.AppApi
import com.yuki.vision.app.net.AppCache
import com.yuki.vision.mvp.model.entity.CategoryResponse
import com.yuki.xndroid.base.mvp.XModel
import com.yuki.xndroid.di.scope.FragmentScope
import com.yuki.xndroid.repository.IRepositoryManager
import io.reactivex.Observable

import javax.inject.Inject


@FragmentScope
class CategoryModel @Inject
constructor(repositoryManager: IRepositoryManager) : XModel(repositoryManager) {
    /**
     * 获取分类信息
     */
    fun initData(): Observable<ArrayList<CategoryResponse>> {
        return Observable.just(mRepositoryManager.http(AppApi::class.java).getCategory())
                .flatMap {
                    mRepositoryManager.cache(AppCache::class.java).getCategory(it)
                }
    }
}
