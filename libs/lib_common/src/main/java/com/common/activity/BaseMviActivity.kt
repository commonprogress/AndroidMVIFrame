package com.common.activity

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.viewbinding.ViewBinding
import com.base.commonality.base.activity.BaseActivity
import com.common.R
import com.common.widget.StatusViewOwner
import com.gyf.immersionbar.ImmersionBar

/**
 * 基类,做一些统一方法的处理 比如：im
 */
abstract class BaseMviActivity<T : ViewBinding> : BaseActivity<T>() {
    protected lateinit var mStatusViewUtil: StatusViewOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStatusViewUtil = StatusViewOwner(this, getStatusOwnerView()) {
            retryRequest()
        }
    }

    /*
      此方法在setContentView之后调用，在此次适配状态栏,默认为白色背景黑色字体，
      如需定制，则设置enableWhiteStatusBar为false,然后覆盖customInitStatusBar方法
    */
    override fun onContentChanged() {
        super.onContentChanged()
        try {
            if (enableWhiteStatusBar()) {
                ImmersionBar.with(this)
                    .fitsSystemWindows(fitsSystemWindows())
                    .statusBarColor(statusBarColor())
                    .statusBarDarkFont(statusBarDarkFont())
                    .navigationBarColor(navigationBarColor(), navigationBarAlpha())
                    .navigationBarDarkIcon(navigationBarDarkIcon())
                    .keyboardEnable(keyboardEnable())
                    .init()
            } else {
                customInitStatusBar()
            }
        } catch (ex: Exception) {
            Log.e("BaseActivity", "initStatusBar Exception:" + ex.message)
        }
    }

    protected open fun enableWhiteStatusBar(): Boolean {
        return true
    }

    protected open fun fitsSystemWindows(): Boolean {
        return true
    }

    protected open fun statusBarColor(): Int {
        return R.color.white
    }

    protected open fun navigationBarColor(): Int {
        return R.color.black
    }

    protected open fun navigationBarAlpha(): Float {
        return 0.5f
    }

    protected open fun statusBarDarkFont(): Boolean {
        return true
    }

    protected open fun navigationBarDarkIcon(): Boolean {
        return false
    }

    protected open fun keyboardEnable(): Boolean {
        return true
    }

    protected open fun customInitStatusBar() {}

    // 标题栏 必须实现
    override fun initView(savedInstanceState: Bundle?) {
        if (setTitleBar() != 0) {
            val titleBar: View = mBinding.root.findViewById(setTitleBar())
            ImmersionBar.setTitleBar(this, titleBar)
        }
    }

    // 标题栏 必须重写
    open fun setTitleBar(): Int {
        return 0
    }

    /**
     * 重新请求
     */
    open fun retryRequest() {}

    /**
     * 请求 返回数据注册
     */
    open fun registerEvent() {}

    /**
     * 展示Loading、Empty、Error视图等
     */
    open fun getStatusOwnerView(): View? {
        return null
    }


}