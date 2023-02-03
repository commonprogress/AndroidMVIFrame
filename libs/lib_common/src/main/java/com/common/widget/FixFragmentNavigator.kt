package com.common.widget

import android.content.Context
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavOptions
import androidx.navigation.NavDestination
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import java.lang.Exception
import java.util.ArrayDeque

/**
 * 自定义的FragmentNavigator
 */
@Navigator.Name("fixFragment") //这是新的Navigator得名称,千万别忘了加
class FixFragmentNavigator(
    private val mContext: Context,
    private val mFragmentManager: FragmentManager,
    private val mContainerId: Int
) : FragmentNavigator(
    mContext, mFragmentManager, mContainerId
) {
    private val TAG = "FixFragmentNavigator"
    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring navigate() call: FragmentManager has already"
                        + " saved its state"
            )
            return null
        }
        var className = destination.className
        if (className[0] == '.') {
            className = mContext.packageName + className
        }
        var frag = mFragmentManager.findFragmentByTag(className)
        if (null == frag) {
            //不存在，则创建
            frag = instantiateFragment(mContext, mFragmentManager, className, args)
        }
        frag.arguments = args
        val ft = mFragmentManager.beginTransaction()
        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

//        ft.replace(mContainerId, frag);
        val fragments = mFragmentManager.fragments
        val isAdd = false
        for (fragment in fragments) {
//            if(fragment.getClass().getCanonicalName().equals(frag.getClass().getCanonicalName())){
//                isAdd = true;
//            }else {
            ft.hide(fragment!!)
            //            }
        }
        if (!isAdd && !frag.isAdded) {
            ft.add(mContainerId, frag, className)
        }
        ft.show(frag)
        ft.setPrimaryNavigationFragment(frag)
        @IdRes val destId = destination.id

        //通过反射获取mBackStack
        val mBackStack: ArrayDeque<Int>
        try {
            val field = FragmentNavigator::class.java.getDeclaredField("mBackStack")
            field.isAccessible = true
            mBackStack = field[this] as ArrayDeque<Int>
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        val initialNavigation = mBackStack.isEmpty()
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && mBackStack.peekLast() == destId)
        val isAdded: Boolean
        isAdded = if (initialNavigation) {
            true
        } else if (isSingleTopReplacement) {
            if (mBackStack.size > 1) {
                mFragmentManager.popBackStack(
                    generateBackStackName(mBackStack.size, mBackStack.peekLast()),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                ft.addToBackStack(generateBackStackName(mBackStack.size, destId))
            }
            false
        } else {
            ft.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
            true
        }
        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key!!, value!!)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        return if (isAdded) {
            mBackStack.add(destId)
            destination
        } else {
            null
        }
    }

    //navigate需要的方法重复类直接复制过来就可以
    private fun generateBackStackName(backStackIndex: Int, destId: Int): String {
        return "$backStackIndex-$destId"
    }
}