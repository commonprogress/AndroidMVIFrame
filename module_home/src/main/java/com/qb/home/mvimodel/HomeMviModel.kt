package  com.qb.home.mvimodel

import com.base.commonality.base.viewmodel.BaseViewModel
import com.base.commonality.base.viewmodel.ISingleUiState
import com.base.commonality.base.viewmodel.IUiState
import com.qb.home.repo.HomeRepo
import com.qb.home.state.BannerUiState
import com.qb.home.state.DetailUiState

/**
 * 类描述: 闪屏页配置信息
 * 创建人: jhonjson
 * 创建时间: 2021/7/22
 */
class HomeMviModel : BaseViewModel<IUiState, ISingleUiState>() {
    private val mLoginRepo = HomeRepo()

    /**
     * 获取banner信息
     */
    fun loadBannerData() {
        requestDataWithFlow(
            showLoading = true,
            request = { mLoginRepo.requestWanData("12345") },
            successCallback = { data ->
                sendSingleUiState {
                    BannerUiState.SUCCESS(data)
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
                sendSingleUiState {
                    DetailUiState.SUCCESS(data)
                }
            },
            failCallback = {}
        )
    }


}