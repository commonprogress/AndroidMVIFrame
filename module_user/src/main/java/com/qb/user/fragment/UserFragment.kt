package com.qb.user.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.common.activity.LazyBaseMviFragment
import com.common.route.RouteUserUtils
import com.qb.user.databinding.FragmentUserBinding


@Route(path = RouteUserUtils.User_Fragment)
class UserFragment : LazyBaseMviFragment<FragmentUserBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

    }

    override fun lazyData() {
//        TODO("Not yet implemented")
    }

    override fun initData() {

    }


    override fun getViewBing(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserBinding {
        return FragmentUserBinding.inflate(layoutInflater)
    }

}