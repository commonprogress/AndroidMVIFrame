package com.common.route

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.common.activity.BaseWebViewActivity
import com.common.constant.CommonConstant
import com.common.utils.log


/**
 * @author jhonjson
 * @date 2021/7/27.
 * description：路由跳转公共类
 */
object GoRouteUtils {
    var Tag : String = "GoRouteUtils"

    /**
     * 获取fragment
     */
    fun getFragment(tag: String): Fragment {
        return ARouter.getInstance().build(tag).navigation() as Fragment
    }


    /**
     * 跳转 webview
     */
    fun goBaseWebViewActivity(context: Context, url: String) {
        log( "$Tag  goBaseWebViewActivity ")
        var mIntent = Intent(context, BaseWebViewActivity::class.java)
        mIntent.putExtra(CommonConstant.KEY_URL,url)
        context.startActivity(mIntent)
    }

}