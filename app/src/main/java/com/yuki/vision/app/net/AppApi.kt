package com.yuki.vision.app.net

import com.yuki.vision.mvp.model.entity.CategoryResponse
import com.yuki.vision.mvp.model.entity.HomeResponse
import com.yuki.vision.mvp.model.entity.HomeResponse.Issue
import com.yuki.vision.mvp.model.entity.TabInfoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


/**
 * 项目：Xndroid
 * 作者：Yuki - 2018/1/15
 * 邮箱：125508663@qq.com
 **/

interface AppApi {
    companion object {
        const val BASE_URL = "http://baobab.kaiyanapp.com/api/"
    }

    /**
     * 首页精选
     */
    @GET("v2/feed")
    fun getFirstHomeData(): Observable<HomeResponse>

    /**
     * 根据 nextPageUrl 请求数据下一页数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeResponse>


    /**
     * 获取搜索信息
     */
    @GET("v1/search?&num=10&start=10")
    fun getSearchData(@Query("query") query: String): Observable<HomeResponse.Issue>

    /**
     * 热门搜索词
     */
    @GET("v3/queries/hot")
    fun getHotWord(): Observable<ArrayList<String>>

    /**
     * 获取更多的 Issue
     */
    @GET
    fun getMoreIssue(@Url url: String): Observable<Issue>


    /**
     * 根据item id获取相关视频
     */
    @GET("v4/video/related?")
    fun getRelatedData(@Query("id") id: Long): Observable<Issue>


    /**
     * 获取分类
     */
    @GET("v4/categories")
    fun getCategory(): Observable<ArrayList<CategoryResponse>>

    /**
     * 获取分类详情List
     */
    @GET("v4/categories/videoList?")
    fun getCategoryDetailList(@Query("id") id: Long): Observable<Issue>

    /**
     * 获取更多的 Issue
     */
    @GET
    fun getIssueData(@Url url: String): Observable<Issue>

    /**
     * 获取全部排行榜的Info（包括，title 和 Url）
     */
    @GET("v4/rankList")
    fun getRankList(): Observable<TabInfoResponse>


    /**
     * 关注
     */
    @GET("v4/tabs/follow")
    fun getFollowInfo(): Observable<Issue>

    /**
     * 作者信息
     */
    //		@GET("v4/pgcs/detail/tab?")
    //		fun getAuthorInfo(@Query("id") id: Long):Observable<AuthorInfoBean>

}