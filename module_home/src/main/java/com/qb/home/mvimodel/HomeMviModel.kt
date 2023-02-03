package  com.qb.home.mvimodel

import android.os.Bundle
import com.base.commonality.base.viewmodel.BaseViewModel
import com.qb.home.repo.HomeRepo
import com.qb.home.state.BannerUiState
import com.qb.home.state.DetailUiState
import com.qb.home.state.MviSingleUiState
import com.qb.home.state.MviState

/**
 * 类描述: 闪屏页配置信息
 * 创建人: jhonjson
 * 创建时间: 2021/7/22
 */
class HomeMviModel : BaseViewModel<MviState, MviSingleUiState>() {
    private val mLoginRepo = HomeRepo()

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
            request = { mLoginRepo.requestWanData("12345") },
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
            request = { mLoginRepo.requestRankData() },
            successCallback = { data ->
                sendUiState {
                    copy(detailUiState = DetailUiState.SUCCESS(data))
                }
            },
            failCallback = {}
        )
    }



}