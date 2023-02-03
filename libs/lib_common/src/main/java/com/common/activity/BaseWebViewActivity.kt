package com.common.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.LinearLayout
import com.common.R
import com.common.constant.CommonConstant
import com.common.databinding.ActivityBaseWebviewBinding
import com.common.utils.log
import com.common.widget.WebLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient

/**
 * 基类 通用webview
 */
class BaseWebViewActivity : BaseMviActivity<ActivityBaseWebviewBinding>() {
    var mAgentWeb: AgentWeb? = null
    var mUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    private val mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            if (mBinding.titleContainer != null) {
                mBinding.titleContainer.setTitle(title)
            }
        }
    }

    override fun initData() {
        mUrl = intent.getStringExtra(CommonConstant.KEY_URL)
        val p = System.currentTimeMillis()

        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(
                mBinding.container,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            .useDefaultIndicator()
            .setWebChromeClient(mWebChromeClient)
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setWebLayout(WebLayout(this))
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .go(getUrl());

        val n = System.currentTimeMillis()
        Log.e("Info", "init used time:" + (n - p))
    }

    private fun getUrl(): String? {
        log("getUrl mUrl   $mUrl ")
        if (mUrl.isNullOrEmpty()){
            return ""
        }
        return mUrl
    }


    /**
     * 调用Js，无参数方法
     * @param mMethodName 方法名
     */
    fun quickCallJs(mMethodName: String) {
        mAgentWeb?.jsAccessEntrace?.quickCallJs(mMethodName)
    }

    /**
     * 调用Js，无参数方法
     * @param mMethodName 方法名
     * @param mParamName 一个参数方法
     */
    fun quickCallJs(mMethodName: String, vararg mParamName: String) {
        mAgentWeb?.jsAccessEntrace?.quickCallJs(mMethodName, *mParamName)
    }

    /**
     * 调用Js，无参数方法
     * @param mMethodName 方法名
     * @param callback 回调
     * @param mParamName 多参数方法
     */
    fun quickCallJs(mMethodName: String, callback: ValueCallback<String>) {
        mAgentWeb?.jsAccessEntrace?.quickCallJs(mMethodName, callback)
    }


    /**
     * Js调用原生
     * @param mMethodName 方法名
     * @param any 多参数方法
     */
//    @JavascriptInterface
//    open fun uploadFile() {
//    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb!!.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb?.webLifeCycle?.onDestroy()
    }




    override fun getViewBing(layoutInflater: LayoutInflater): ActivityBaseWebviewBinding {
        return ActivityBaseWebviewBinding.inflate(layoutInflater)
    }
}