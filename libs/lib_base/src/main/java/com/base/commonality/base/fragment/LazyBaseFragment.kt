package com.base.commonality.base.fragment

import androidx.viewbinding.ViewBinding

/**
 * 懒加载基类
 * 新的方式进行懒加载
 */
abstract class LazyBaseFragment<T : ViewBinding> : BaseFragment<T>() {
    private var isFirst = true

    /**
     * 请求加在数据
     *
     * 加载第一次请求数据
     */
    abstract fun lazyData()


    /**
     * 要显示标题栏，但是不需要适配全面屏的可以重写此方法 返回false
     *
     * 默认返回true
     */
    open fun isShow(): Boolean {
        return true
    }

    fun setIsFirst(isFirst : Boolean){
        this.isFirst = isFirst
    }

    override fun onResume() {
        super.onResume()
        if (isFirst) {
            isFirst = false
            lazyData()
        }
    }
}