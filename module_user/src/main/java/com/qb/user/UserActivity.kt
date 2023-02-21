package com.qb.user

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.commonality.base.viewmodel.LoadUiState
import com.common.activity.BaseMviActivity
import com.common.route.RouteUserUtils
import com.base.commonality.ktx.clickDelay
import com.base.commonality.ktx.flowWithLifecycle2
import com.google.gson.Gson
import com.qb.user.databinding.ActivityUserBinding
import com.qb.user.mvimodel.UserMviModel
import com.qb.user.state.BannerUiState
import com.qb.user.state.DetailUiState

@Route(path = RouteUserUtils.User_UserActivity)
class UserActivity : BaseMviActivity<ActivityUserBinding>() {
    private val mViewModel: UserMviModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.tvUser.clickDelay {
            mViewModel.loadBannerData()
        }

        mBinding.tvUser1.clickDelay {
            mViewModel.loadDetailData()
        }

        registerEvent()

    }

    override fun registerEvent() {
        /**
         * Load加载事件 Loading、Error、ShowMainView
         */
        mViewModel.loadUiStateFlow.flowWithLifecycle2(this) { state ->
            when (state) {
                is LoadUiState.Error -> mStatusViewUtil.showErrorView(state.msg)
                is LoadUiState.ShowMainView -> mStatusViewUtil.showMainView()
                is LoadUiState.Loading -> mStatusViewUtil.showLoadingView(state.isShow)
            }
        }

        mViewModel.sUiStateFlow.flowWithLifecycle2(this) { state ->
            when (state) {
                is BannerUiState.INIT -> {}
                is BannerUiState.SUCCESS -> {
                    val imgs = mutableListOf<String>()
                    for (model in state.models) {
                        imgs.add(model.imagePath)
                    }
                    mBinding.tvShowUser.text = "显示Banner数据：${Gson().toJson(imgs)}"
                }
                is DetailUiState.INIT -> {}
                is DetailUiState.SUCCESS -> {
                    val list = state.detail.datas
                    mBinding.tvShowUser1.text = "显示Banner数据：${Gson().toJson(list)}"
                }
            }
        }
    }


    override fun getViewBing(layoutInflater: LayoutInflater): ActivityUserBinding {
        return ActivityUserBinding.inflate(layoutInflater)
    }
}