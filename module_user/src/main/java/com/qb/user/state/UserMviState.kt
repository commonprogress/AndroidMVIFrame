package com.qb.user.state

import com.base.commonality.base.viewmodel.ISingleUiState
import com.base.commonality.base.viewmodel.IUiState
import com.qb.user.bean.RankBean
import com.qb.user.bean.WanBean

data class MviState(val bannerUiState: BannerUiState, val detailUiState: DetailUiState?) : IUiState

sealed class BannerUiState {
    object INIT : BannerUiState()
    data class SUCCESS(val models: List<WanBean>) : BannerUiState()
}

data class MviSingleUiState(val message: String) : ISingleUiState
sealed class DetailUiState {
    object INIT : DetailUiState()
    data class SUCCESS(val detail: RankBean) : DetailUiState()
}