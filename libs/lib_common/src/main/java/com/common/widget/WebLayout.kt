package com.common.widget

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import com.common.R
import com.just.agentweb.IWebLayout
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class WebLayout(private val mActivity: Activity) : IWebLayout<WebView, ViewGroup> {
    var mSmartRefreshLayout: SmartRefreshLayout = LayoutInflater.from(mActivity)
        .inflate(R.layout.fragment_webview, null) as SmartRefreshLayout
    private var mWebView: WebView? = null

    override fun getLayout(): ViewGroup {
        return mSmartRefreshLayout
    }

    override fun getWebView(): WebView? {
        return mWebView
    }

    init {
        mWebView = mSmartRefreshLayout.findViewById(R.id.webView)
    }
}