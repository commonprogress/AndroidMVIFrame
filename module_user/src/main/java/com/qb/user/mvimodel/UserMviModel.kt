package com.qb.user.mvimodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.base.commonality.base.viewmodel.BaseViewModel
import com.base.commonality.base.viewmodel.ISingleUiState
import com.base.commonality.base.viewmodel.IUiState
import com.qb.user.repo.UserRepo
import com.qb.user.state.BannerUiState
import com.qb.user.state.DetailUiState

/**
 * 类描述: 闪屏页配置信息
 * 创建人: jhonjson
 * 创建时间: 2021/7/22
 */
class UserMviModel() : BaseViewModel<IUiState, ISingleUiState>() {
    var value = MutableLiveData<String>()
    private val mUserRepo = UserRepo()
    fun initData(bundle: Bundle?) {

    }

    /**
     * 获取banner信息
     */
    fun loadBannerData() {
        requestDataWithFlow(
            showLoading = true,
            request = { mUserRepo.requestWanData("12345") },
            successCallback = { data ->
                sendSingleUiState {BannerUiState.SUCCESS(data) }
            },
            failCallback = {}
        )
    }

    //请求List数据
    fun loadDetailData() {
        requestDataWithFlow(
            showLoading = false,
            request = { mUserRepo.requestRankData() },
            successCallback = { data ->
                sendSingleUiState { DetailUiState.SUCCESS(data) }
            },
            failCallback = {}
        )
    }

}