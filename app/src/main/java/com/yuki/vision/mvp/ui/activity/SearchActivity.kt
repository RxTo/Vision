package com.yuki.vision.mvp.ui.activity

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.ethanhua.skeleton.ViewReplacer
import com.google.android.flexbox.*
import com.yuki.vision.R
import com.yuki.vision.app.adapter.recycler.SlimAdapter
import com.yuki.vision.app.config.AppLoadMore
import com.yuki.vision.app.durationFormat
import com.yuki.vision.app.goToVideoPlayer
import com.yuki.vision.app.toast
import com.yuki.vision.app.utils.StatusBarUtil
import com.yuki.vision.app.utils.ViewAnimUtils
import com.yuki.vision.di.component.DaggerSearchComponent
import com.yuki.vision.di.module.SearchModule
import com.yuki.vision.mvp.model.entity.HomeResponse
import com.yuki.vision.mvp.model.entity.HomeResponse.Issue
import com.yuki.vision.mvp.presenter.SearchPresenter
import com.yuki.xndroid.base.XActivity
import com.yuki.xndroid.di.component.AppComponent
import com.yuki.xndroid.imageLoader.ImageLoader
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item_search_header.view.*
import javax.inject.Inject

/**
 * 项目：Vision
 * 作者：Yuki - 2018/1/26
 * 邮箱：125508663@qq.com
 **/

class SearchActivity : XActivity<SearchPresenter>() {
    private lateinit var viewReplacer: ViewReplacer
    private lateinit var keyWords: String
    private val searchAdapter by lazy { SlimAdapter.create<Issue.Item>(mRecyclerView_result) }
    private val hotSearchAdapter by lazy { SlimAdapter.create<String>(mRecyclerView_hot) }
    @Inject
    lateinit var layoutManager: LinearLayoutManager
    @Inject
    lateinit var loadMore: AppLoadMore


    private val headerView by lazy { LayoutInflater.from(this).inflate(R.layout.item_search_header, mRecyclerView_result.parent as ViewGroup, false) }

    override fun initActivityComponent(appComponent: AppComponent) {
        DaggerSearchComponent.builder()
                .appComponent(appComponent)
                .searchModule(SearchModule(this))
                .build()
                .inject(this)
    }

    override fun initLayoutId(): Int = R.layout.activity_search

    override fun initView(savedInstanceState: Bundle?) {
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)

        //多状态
        viewReplacer = ViewReplacer(layout_content_result)

        //设置进场动画
        initTransition()

        //初始化查询结果的 RecyclerView
        mPresenter?.requestHotWord()
        hotSearchAdapter
                .register(R.layout.item_flow_text, { holder, item, _ ->
                    holder.text(R.id.tv_title, item)
                    val params = holder.find<TextView>(R.id.tv_title).layoutParams
                    if (params is FlexboxLayoutManager.LayoutParams) {
                        params.flexGrow = 1.0f
                    }
                })
                .click { _, _, position ->
                    keyWords = position.toString()
                    mPresenter?.requestSearchResult(keyWords)
                    closeKeyBord(et_search_view, this)
                }
        searchAdapter
                .layout(layoutManager)
                .head(headerView)
                .loadMore(loadMore, {
                    mPresenter?.requestMoreIssue()
                })
                .register(R.layout.item_category_detail, { holder, item, _ ->
                    val itemData = item?.data
                    val cover = itemData?.cover?.feed ?: ""
                    // 加载封页图
                    ImageLoader.loadImage(holder.find(R.id.iv_image), cover, R.mipmap.placeholder_banner)
                    holder.text(R.id.tv_title, itemData?.title ?: "")

                    // 格式化时间
                    val timeFormat = durationFormat(itemData?.duration)

                    holder.text(R.id.tv_tag, "${itemData?.category}/$timeFormat")
                })
                .click { _, view, position ->
                    goToVideoPlayer(this, view, position as Issue.Item?)
                }
                .loadMore(loadMore, {
                    mPresenter?.requestMoreIssue()
                })

    }

    override fun initEvent() {
        //取消
        tv_cancel.setOnClickListener { onBackPressed() }
        //键盘的搜索按钮
        et_search_view.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                closeKeyBord(et_search_view, applicationContext)
                keyWords = et_search_view.text.toString().trim()
                if (keyWords.isEmpty()) {
                    toast("请输入你感兴趣的关键词")
                } else {
                    closeKeyBord(et_search_view, this)
                    mPresenter?.requestSearchResult(keyWords)
                }
            }
            false
        }

    }

    /**
     * 设置热门关键词
     */
    fun initHotWord(data: ArrayList<String>) {
        //初始化HotkeyRecyclerView
        val flexBoxLayoutManager = FlexboxLayoutManager(this)
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP      //按正常方向换行
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW   //主轴为水平方向，起点在左端
        flexBoxLayoutManager.alignItems = AlignItems.CENTER    //定义项目在副轴轴上如何对齐
        flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START  //多个轴对齐方式
        hotSearchAdapter.layout(flexBoxLayoutManager)
                .initNew(data)
    }

    /**
     * 加载数据失败时展示错误信息
     */
    fun initDataFail(handleRxError: String?) {
        toast(handleRxError.toString())
    }

    /**
     * 设置搜索关键词返回的结果
     */
    fun initSearchResult(issue: HomeResponse.Issue) {
        viewReplacer.restore()
        searchAdapter.initNew(issue.itemList)
        headerView.tv_search_count.text = String.format(resources.getString(R.string.search_result_count), keyWords, issue.total)
    }

    fun initMoreSearchResult(issue: Issue) {
        searchAdapter.initMore(issue.itemList)
    }

    fun initMoreFail(msg: String?) {
        toast(msg.toString())
        searchAdapter.loadMoreFail()
    }

    fun showSearchLoading() {
        if (layout_hot_words.visibility != View.GONE)
            layout_hot_words.visibility = View.GONE
        viewReplacer.replace(R.layout.view_loading)
    }

    /**
     * 查询结果为空
     */
    fun showSearchEmpty() {
        viewReplacer.replace(R.layout.view_empty)
    }

    fun showSearchError() {
        viewReplacer.replace(R.layout.view_error)
    }


    /**
     * 加载动画
     * */
    private fun initTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpEnterAnimation() // 入场动画
            setUpExitAnimation() // 退场动画
        } else {
            setUpView()
        }
    }


    /**
     * 进场动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpEnterAnimation() {
        val transition = TransitionInflater.from(this).inflateTransition(R.transition.arc_motion)
        window.sharedElementEnterTransition = transition
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {

            }

            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                animateRevealShow()
            }

            override fun onTransitionCancel(transition: Transition) {

            }

            override fun onTransitionPause(transition: Transition) {

            }

            override fun onTransitionResume(transition: Transition) {

            }
        })
    }

    /**
     * 展示动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateRevealShow() {
        ViewAnimUtils.animateRevealShow(this, rel_frame, fab_circle.width / 2, R.color.backgroundColor,
                object : ViewAnimUtils.OnRevealAnimationListener {
                    override fun onRevealHide() {

                    }

                    override fun onRevealShow() {
                        setUpView()
                    }
                })
    }

    private fun setUpView() {
        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        animation.duration = 300
        rel_container.startAnimation(animation)
        rel_container.visibility = View.VISIBLE
        //打开软键盘
        openKeyBord(et_search_view, applicationContext)
    }

    /**
     * 退场动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpExitAnimation() {
        val fade = Fade()
        window.returnTransition = fade
        fade.duration = 400
    }

    /**
     * 打开键盘
     */
    private fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭键盘
     */
    private fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }


    fun initDataEnd() {
        searchAdapter.loadMoreEnd()
    }

    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        closeKeyBord(et_search_view, this)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            finishAfterTransition()
        else
            finish()
    }


}
