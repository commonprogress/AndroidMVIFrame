package com.qb.user.mvimodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.base.commonality.base.viewmodel.BaseViewModel
import com.qb.user.repo.UserRepo
import com.qb.user.state.BannerUiState
import com.qb.user.state.DetailUiState
import com.qb.user.state.MviSingleUiState
import com.qb.user.state.MviState

/**
 * 类描述: 闪屏页配置信息
 * 创建人: jhonjson
 * 创建时间: 2021/7/22
 */
class UserMviModel() : BaseViewModel<MviState, MviSingleUiState>() {
    var value = MutableLiveData<String>()
    private val mUserRepo = UserRepo()
    fun initData(bundle: Bundle?) {

    }

    override fun initUiState(): MviState {
        return MviState(BannerUiState.INIT, DetailUiState.INIT)
    }

    /**
     * 获取banner信息
     */
    fun loadBannerData() {
        requestDataWithFlow(
            showLoading = true,
            request = { mUserRepo.requestWanData("12345") },
            successCallback = { data ->
                sendUiState {
                    copy(bannerUiState = BannerUiState.SUCCESS(data))
                }
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
                sendUiState {
                    copy(detailUiState = DetailUiState.SUCCESS(data))
                }
            },
            failCallback = {}
        )
    }

}