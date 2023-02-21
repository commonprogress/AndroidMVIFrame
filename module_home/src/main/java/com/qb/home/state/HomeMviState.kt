package com.qb.home.state

import com.base.commonality.base.viewmodel.ISingleUiState
import com.base.commonality.base.viewmodel.IUiState
import com.qb.home.bean.RankBean
import com.qb.home.bean.WanBean


sealed class BannerUiState :ISingleUiState{
    object INIT : BannerUiState()
    data class SUCCESS(val models: List<WanBean>) : BannerUiState()
}

sealed class DetailUiState :ISingleUiState{
    object INIT : DetailUiState()
    data class SUCCESS(val detail: RankBean) : DetailUiState()
}