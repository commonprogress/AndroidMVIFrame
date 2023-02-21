package com.mvi.android.mvimodel

import android.os.Bundle
import com.base.commonality.base.viewmodel.BaseViewModel
import com.base.commonality.base.viewmodel.ISingleUiState
import com.base.commonality.base.viewmodel.IUiState
import com.common.extend.toast
import com.mvi.android.repo.MainRepo
import com.mvi.android.state.BannerUiState
import com.mvi.android.state.DetailUiState

/**
 * 类描述: 闪屏页配置信息
 * 创建人: jhonjson
 * 创建时间: 2021/7/22
 */
class MainMviModel() :
    BaseViewModel<IUiState, ISingleUiState>() {
    private val mMainRepo = MainRepo()

    fun initData(bundle: Bundle?) {

    }

//    override fun initUiState(): MviState {
//        return MviState(BannerUiState.INIT, DetailUiState.INIT)
//    }

    /**
     * 获取banner信息
     */
    fun loadBannerData() {
        requestDataWithFlow(
            showLoading = true,
            request = { mMainRepo.requestWanData("12345") },
            successCallback = { data ->
                sendSingleUiState { BannerUiState.SUCCESS(data) }
            },
            failCallback = {
                it.toast()
            }
        )
    }

    //请求List数据
    fun loadDetailData() {
        requestDataWithFlow(
            showLoading = false,
            request = { mMainRepo.requestRankData() },
            successCallback = { data ->
                sendSingleUiState { DetailUiState.SUCCESS(data)
                }
            },
        )
    }

}