package com.qb.login.repo

import com.base.commonality.bean.BaseData
import com.base.commonality.bean.BaseRepository
import com.common.net.RetrofitUtil
import com.qb.login.bean.RankBean
import com.qb.login.bean.WanBean
import com.qb.login.service.LoginService

class LoginRepo : BaseRepository() {
    private val service = RetrofitUtil.getService(LoginService::class.java)

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