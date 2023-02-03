package com.qb.home.repo

import com.base.commonality.bean.BaseData
import com.base.commonality.bean.BaseRepository
import com.common.net.RetrofitUtil
import com.qb.home.bean.RankBean
import com.qb.home.bean.WanBean
import com.qb.home.service.HomeService

class HomeRepo : BaseRepository() {
    private val service = RetrofitUtil.getService(HomeService::class.java)

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