package com.yuki.vision.mvp.presenter

import com.yuki.vision.app.applySchedulers
import com.yuki.vision.mvp.model.SearchModel
import com.yuki.vision.mvp.ui.activity.SearchActivity
import com.yuki.xndroid.base.mvp.XPresenter
import com.yuki.xndroid.kit.RxErrorListener
import javax.inject.Inject

/**
 * 项目：Vision
 * 作者：Yuki - 2018/1/28
 * 邮箱：125508663@qq.com
 **/

class SearchPresenter @Inject
constructor(private val rxErrorListener: RxErrorListener, model: SearchModel,
            view: SearchActivity) : XPresenter<SearchModel, SearchActivity>(model, view) {

    private var nextPageUrl: String? = null

    /**
     * 请求热门关键词的数据
     */
    fun requestHotWord() {
        mModel.getHotWord()
                .applySchedulers(mView)
                .subscribe({
                    mView.initHotWord(it)
                }, {
                    mView.initDataFail(rxErrorListener.handleRxError(it))
                })
    }


    /**
     * 搜索关键词返回的结果
     */
    fun requestSearchResult(words: String) {
        mModel.getSearchResult(words)
                .applySchedulers(mView)
                .doOnSubscribe({
                    mView.showSearchLoading()
                })
                .subscribe({
                    nextPageUrl = it.nextPageUrl
                    if (it.count > 0 && it.itemList.size > 0)
                        mView.initSearchResult(it)
                    else
                        mView.showSearchEmpty()
                }, {
                    mView.initDataFail(rxErrorListener.handleRxError(it))
                    mView.showSearchError()
                })
    }

    /**
     * 加载更多数据
     */
    fun requestMoreIssue() {
        nextPageUrl?.let {
            mModel.getMoreIssue(it)
                    .applySchedulers(mView)
                    .subscribe({
                        nextPageUrl = it.nextPageUrl
                        mView.initMoreSearchResult(it)
                    }, {
                        mView.initMoreFail(rxErrorListener.handleRxError(it))
                    })
        } ?: mView.initDataEnd()
    }
}
	
