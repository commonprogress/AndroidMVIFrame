package com.common


import android.R
import android.app.Application
import android.util.Log
import com.base.commonality.BaseAppliction
import com.base.commonality.utils.IOUtils
import com.common.net.RetrofitUtil
import com.common.utils.log
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator


class CommonApplication : BaseAppliction() {

    companion object {
        /**
         * 当前手机时间和服务器时间的偏移量
         *
         * 直播PK用到
         */
        var currentTimeDeviation: Long = 0
    }

    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(DefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.darker_gray, R.color.white) //全局设置主题颜色
             ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        })
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }

    }

    override fun onCreate() {
        super.onCreate()
        Log.e("onCreate ","onCreate ")
        initMainDesk()
    }

    override fun onTerminate() {
        super.onTerminate()
        IOUtils.cancel()
    }

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    fun initByBackstage() {
//        IOUtils.ioScope.launch(Dispatchers.Unconfined) {
//            delay(3000)
//
//        }
    }

    /**
     * 需要立即初始化的放在这里
     */
    private fun initMainDesk() {
        initMMKV()
        initARouter()
        initRetrofitUtil()
        initFileDownloader()
        Log.e("initMainDesk","initMainDesk -->> init complete")
    }


    /**
     * 文件下载引擎 初始化
     */
    private fun initFileDownloader(): String {
//        Log.e("文件下载引擎 初始化", "文件下载引擎 初始化")
        FileDownloader.setupOnApplicationOnCreate(mContext as Application)
            .connectionCreator(
                FileDownloadUrlConnection.Creator(
                    FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15000) // set connection timeout.
                        .readTimeout(15000) // set read timeout.
                )
            )
            .commit()
        log("CApplication 文件下载引擎 -->> init complete")
        return "CApplication 文件下载引擎 -->> init complete"
    }

    /**
     * 初始化 RetrofitUtil
     */
    fun initRetrofitUtil(): String {
        //初始化Retrofit
        RetrofitUtil.initRetrofit()
        Log.e("initRetrofitUtil","RetrofitUtil -->> init complete")
        return "RetrofitUtil -->> init complete"
    }

}