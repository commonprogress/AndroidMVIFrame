package com.common.activity

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.base.commonality.base.fragment.LazyBaseFragment
import com.common.widget.StatusViewOwner

/**
 * 懒加载基类
 * 新的方式进行懒加载
 */
abstract class LazyBaseMviFragment<T : ViewBinding> : LazyBaseFragment<T>() {
    protected lateinit var mStatusViewUtil: StatusViewOwner

    // 标题栏 必须实现
    override fun initView(savedInstanceState: Bundle?) {
        mStatusViewUtil = getCurActivity()?.let {
            StatusViewOwner(it, getStatusOwnerView()) {
                retryRequest()
            }
        }!!
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