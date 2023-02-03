package com.qb.user.repo

import com.base.commonality.bean.BaseData
import com.base.commonality.bean.BaseRepository
import com.common.net.RetrofitUtil
import com.qb.user.bean.RankBean
import com.qb.user.bean.WanBean
import com.qb.user.service.UserService

class UserRepo : BaseRepository() {
    private val service = RetrofitUtil.getService(UserService::class.java)

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