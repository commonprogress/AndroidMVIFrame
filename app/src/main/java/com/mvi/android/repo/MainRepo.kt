package com.mvi.android.repo

import com.base.commonality.bean.BaseData
import com.base.commonality.bean.BaseRepository
import com.common.net.RetrofitUtil
import com.mvi.android.bean.RankBean
import com.mvi.android.bean.WanBean
import com.mvi.android.service.MainService

class MainRepo : BaseRepository() {
    private val service = RetrofitUtil.getService(MainService::class.java)

    suspend fun requestWanData(drinkId: String): BaseData<List<WanBean>> {
        val requestBody = jsonRequest()
            .p("drinkId", drinkId)
            .body()
        return executeRequest { service.getBanner(requestBody) }
    }

    suspend fun requestRankData(): BaseData<RankBean> {
        return executeRequest { service.getRankList() }
    }
}