package com.mvi.android.service

import com.base.commonality.bean.BaseData
import com.common.constant.api.ComServerApi
import com.mvi.android.bean.RankBean
import com.mvi.android.bean.WanBean
import okhttp3.RequestBody
import retrofit2.http.*

interface MainService {

    @POST(ComServerApi.API_BANNER)
    suspend fun getBanner(@Body requestBody: RequestBody): BaseData<List<WanBean>>

    @GET(ComServerApi.API_COIN_RANK)
    suspend fun getRankList(): BaseData<RankBean>
}