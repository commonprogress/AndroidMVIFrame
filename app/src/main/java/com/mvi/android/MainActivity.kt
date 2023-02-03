package com.mvi.android

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.NavigatorProvider
import androidx.navigation.fragment.NavHostFragment
import com.jhonjson.androidmvvmframe.databinding.ActivityMainBinding
import com.common.activity.BaseMviActivity
import com.base.commonality.manager.ActivityStackManager
import com.common.extend.toast
import com.common.widget.FixFragmentNavigator
import com.jhonjson.androidmvvmframe.R
import com.mvi.android.mvimodel.MainMviModel
import com.qb.home.fragment.HomeFragment
import com.qb.user.fragment.UserFragment
import kotlin.system.exitProcess

class MainActivity : BaseMviActivity<ActivityMainBinding>() {
    private val mViewModel: MainMviModel by viewModels()
    private lateinit var navFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        mBinding.tvUser.clickDelay {
//            mViewModel.loadBannerData()
//        }
//
//        mBinding.tvUser1.clickDelay {
//            mViewModel.loadDetailData()
//        }
//
//        registerEvent()

        navFragment =
            supportFragmentManager.findFragmentById(R.id.fl_content)!!
        //fragment的重复加载问题和NavController有关
        val navController = NavHostFragment.findNavController(navFragment)
        setFixNavigator(navController)
    }

    /**
     * FixFragmentNavigator的使用
     */
    private fun setFixNavigator(navController: NavController) {
        val provider = navController.navigatorProvider
        //设置自定义的navigator
        val fixFragmentNavigator =
            FixFragmentNavigator(this, navFragment.childFragmentManager, navFragment.id)
        provider.addNavigator(fixFragmentNavigator)
        val navDestinations = initNavGraph(provider, fixFragmentNavigator)
        navController.graph = navDestinations!!
        mBinding.bottomNavigationBar.setOnItemSelectedListener { item ->
            navController.navigate(item.itemId)
            if (item.itemId == R.id.navigation_home) {
            } else if (item.itemId == R.id.navigation_mine) {
//
            }
            true
        }
    }


    private fun initNavGraph(
        provider: NavigatorProvider,
        fragmentNavigator: FixFragmentNavigator
    ): NavGraph? {
        val navGraph = NavGraph(NavGraphNavigator(provider))
        //用自定义的导航器来创建目的地
        val destination1 = fragmentNavigator.createDestination()
        destination1.id = R.id.navigation_home
        destination1.className = HomeFragment::class.java.canonicalName
        navGraph.addDestination(destination1)

        val destination2 = fragmentNavigator.createDestination()
        destination2.id = R.id.navigation_mine
        destination2.className = UserFragment::class.java.canonicalName
        navGraph.addDestination(destination2)
        navGraph.startDestination = destination1.id
        return navGraph
    }



    override fun registerEvent() {
        /**
         * Load加载事件 Loading、Error、ShowMainView
         */
//        mViewModel.loadUiStateFlow.flowWithLifecycle2(this) { state ->
//            when (state) {
//                is LoadUiState.Error -> mStatusViewUtil.showErrorView(state.msg)
//                is LoadUiState.ShowMainView -> mStatusViewUtil.showMainView()
//                is LoadUiState.Loading -> mStatusViewUtil.showLoadingView(state.isShow)
//            }
//        }

//
//        mViewModel.uiStateFlow.flowWithLifecycle2(this, prop1 = MviState::bannerUiState) { state ->
//            when (state) {
//                is BannerUiState.INIT -> {}
//                is BannerUiState.SUCCESS -> {
//                    val imgs = mutableListOf<String>()
//                    for (model in state.models) {
//                        imgs.add(model.imagePath)
//                    }
//                    mBinding.tvShowUser.text = "显示Banner数据：${Gson().toJson(imgs)}"
//                }
//            }
//        }
//
//        mViewModel.uiStateFlow.flowWithLifecycle2(
//            this, Lifecycle.State.STARTED,
//            prop1 = MviState::detailUiState
//        ) { state ->
//            when (state) {
//                is DetailUiState.INIT -> {}
//                is DetailUiState.SUCCESS -> {
//                    val list = state.detail.datas
//                    mBinding.tvShowUser1.text = "显示Banner数据：${Gson().toJson(list)}"
//                }
//            }
//
//        }
    }

    private var clickTime: Long = 0
    private fun exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            "再次点击退出".toast()
            clickTime = System.currentTimeMillis()
        } else {
            ActivityStackManager.finishAllActivity()
            exitProcess(0)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun getViewBing(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}