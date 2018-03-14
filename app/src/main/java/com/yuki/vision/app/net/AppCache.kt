package com.yuki.vision.app.net

import com.yuki.vision.mvp.model.entity.CategoryResponse
import com.yuki.vision.mvp.model.entity.HomeResponse
import com.yuki.vision.mvp.model.entity.HomeResponse.Issue
import com.yuki.vision.mvp.model.entity.TabInfoResponse
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.LifeCache
import java.util.concurrent.TimeUnit

/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/18
 * 邮箱：125508663@qq.com
 **/

interface AppCache {

    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getFirstHomeData(observable: Observable<HomeResponse>): Observable<HomeResponse>

    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getMoreHomeData(observable: Observable<HomeResponse>, dynamicKey: DynamicKey): Observable<HomeResponse>


    /**
     * 获取搜索信息
     */
    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getSearchData(observable: Observable<HomeResponse.Issue>, dynamicKey: DynamicKey): Observable<HomeResponse.Issue>

    /**
     * 热门搜索词
     */
    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getHotWord(observable: Observable<ArrayList<String>>): Observable<ArrayList<String>>

    /**
     * 获取更多的 Issue
     */
    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getMoreIssue(observable: Observable<Issue>, dynamicKey: DynamicKey): Observable<Issue>


    /**
     * 根据item id获取相关视频
     */
    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getRelatedData(observable: Observable<Issue>, dynamicKey: DynamicKey): Observable<Issue>

    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getFollowInfo(observable: Observable<Issue>): Observable<Issue>

    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getIssueData(observable: Observable<Issue>, dynamicKey: DynamicKey): Observable<Issue>

    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getCategory(observable: Observable<ArrayList<CategoryResponse>>): Observable<ArrayList<CategoryResponse>>

    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getCategoryDetailList(observable: Observable<Issue>, dynamicKey: DynamicKey): Observable<Issue>

    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getRankList(observable: Observable<TabInfoResponse>): Observable<TabInfoResponse>


}