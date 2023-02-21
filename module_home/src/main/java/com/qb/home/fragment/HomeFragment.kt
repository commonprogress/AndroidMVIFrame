package com.qb.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.commonality.base.viewmodel.LoadUiState
import com.base.commonality.ktx.clickDelay
import com.base.commonality.ktx.flowWithLifecycle2
import com.common.activity.LazyBaseMviFragment
import com.common.route.GoRouteUtils
import com.common.route.RouteHomeUtils
import com.google.gson.Gson
import com.qb.home.databinding.FragmentHomeBinding
import com.qb.home.mvimodel.HomeMviModel
import com.qb.home.state.BannerUiState
import com.qb.home.state.DetailUiState


@Route(path = RouteHomeUtils.Home_Fragment)
class HomeFragment : LazyBaseMviFragment<FragmentHomeBinding>() {
    private var fragments = mutableListOf<Fragment>()
    private val mViewModel: HomeMviModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        mBinding.tvUser1.clickDelay {
            mViewModel.loadDetailData()
        }

        mBinding.tvWebview.clickDelay {
            GoRouteUtils.goBaseWebViewActivity(requireContext(),"https://www.baidu.com")
        }

        registerEvent()
    }

    override fun lazyData() {
        mViewModel.loadBannerData()
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
                    mBinding.tvShowUser.text = "显示Banner数据1：${Gson().toJson(imgs)}"
                }
                is DetailUiState.INIT -> {}
                is DetailUiState.SUCCESS -> {
                    val list = state.detail.datas
                    mBinding.tvShowUser1.text = "显示Banner数据2：${Gson().toJson(list)}"
                }
            }
        }
    }

    override fun getViewBing(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }


}