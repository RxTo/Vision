package com.yuki.vision.mvp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.yuki.vision.R
import com.yuki.vision.app.utils.StatusBarUtil
import com.yuki.xndroid.base.XFragment
import com.yuki.xndroid.base.mvp.IPresenter
import kotlinx.android.synthetic.main.fragment_mine.*


class MineFragment : XFragment<IPresenter>() {

    public override fun initLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StatusBarUtil.darkMode(_mActivity)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {
        chrome.loadUrl("https://github.com/RxTo")
        chrome.settings.javaScriptEnabled = true
        toolbar.setNavigationOnClickListener {
            onBackPressedSupport()
        }
        chrome.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

        }
    }

    override fun onBackPressedSupport(): Boolean {
        if (chrome.canGoBack()) {
            chrome.goBack()
            return true

        }

        return false
    }


    companion object {

        fun newInstance(): MineFragment {
            val fragment = MineFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


}
