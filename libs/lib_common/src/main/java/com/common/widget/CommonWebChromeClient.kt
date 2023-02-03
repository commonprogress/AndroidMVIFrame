package com.common.widget

import android.util.Log
import android.webkit.WebView
import com.just.agentweb.WebChromeClient

/**
 * @author cenxiaozhong
 * @date 2019/2/19
 * @since 1.0.0
 */
class CommonWebChromeClient : WebChromeClient() {
    override fun onProgressChanged(view: WebView, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        Log.i("CommonWebChromeClient", "onProgressChanged:$newProgress  view:$view")
    }
}