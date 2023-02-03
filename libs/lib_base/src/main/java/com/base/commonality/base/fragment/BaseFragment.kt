package com.base.commonality.base.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.base.commonality.BaseAppliction
import com.base.commonality.base.IBinding
import com.base.commonality.manager.ActivityStackManager

/**
 * 基类，不掺杂业务，如没有必要，不建议修改
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment(), IBinding<VB> {
    lateinit var mBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = getViewBing(inflater, container)
        initView(savedInstanceState)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    /**
     * 初始化 view
     *
     * view监听事件 等
     *
     * 注：ViewModel还没有初始化
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 是否通过ViewModel共享数据
     * 默认不共享
     */
    open fun isShareViewModel(): Boolean {
        return false
    }

    /**
     * 布局文件
     */
    abstract fun getViewBing(layoutInflater: LayoutInflater, container: ViewGroup?): VB

    /**
     * 初始化数据
     * 设置数据
     * 请求接口等
     * 懒加载不不建议使用此方法
     */
    protected open fun initData() = Unit

    /**
     * getCurContext
     */
    open fun getCurActivity(): Activity? {
        return ActivityStackManager.getCurrentActivity()
    }

    /**
     * 判断mBinding是否有初始化
     */
    fun isInitializedBinding(): Boolean {
        return this::mBinding.isInitialized
    }

    /**fragment拦截返回键返回true，不拦截返回false*/
    open fun onBackPressed() {
    }

    /**
     * @param str 弹出的文字
     */
    open fun toast(str: String?) {
        Toast.makeText(BaseAppliction.mContext, str, Toast.LENGTH_SHORT).show()
    }

}