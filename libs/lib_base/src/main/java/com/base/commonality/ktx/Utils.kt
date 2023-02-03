package com.base.commonality.ktx

import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.alibaba.android.arouter.launcher.ARouter
import com.base.commonality.BaseAppliction
import com.base.commonality.interfaces.IEvent
import com.base.commonality.utils.EventBusUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import org.json.JSONObject
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.reflect.KProperty1


/**
 * 使用 Flow 做的简单的轮询
 * 请使用单独的协程来进行管理该 Flow
 * Flow 仍有一些操作符是实验性的 使用时需添加 @InternalCoroutinesApi 注解
 * @param intervals 轮询间隔时间/毫秒
 * @param block 需要执行的代码块
 */
suspend fun startPolling(intervals: Long, block: () -> Unit) {
    flow {
        while (true) {
            delay(intervals)
            emit(0)
        }
    }
        .catch {
//            Log.e("flow", "startPolling: $it")
        }
        .flowOn(Dispatchers.Main)
        .collect { block.invoke() }
}
/**************************************************************************************************/

/**
 * 发送普通EventBus事件
 *
 */
fun sendEvent(event: IEvent) = EventBusUtils.postEvent(event)

/**************************************************************************************************/
/**
 * 阿里路由不带参数跳转
 * @param routerUrl String 路由地址
 */
fun aRouterJump(routerUrl: String) {
    ARouter.getInstance().build(routerUrl).navigation()
}

/**
 * LiveData扩展函数封装
 */
fun <T> FragmentActivity.observe(liveData: MutableLiveData<T>, observer: (t: T) -> Unit) {
    liveData.observe(this) { observer(it) }
}

inline fun <T> Flow<T>.flowWithLifecycle2(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.(T) -> Unit,
) = lifecycleOwner.lifecycleScope.launch {
    //前后台切换时可以重复订阅数据。如：Lifecycle.State是STARTED，那么在生命周期进入 STARTED 状态时开始任务，在 STOPED 状态时停止订阅
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect { block(it) }
    //另外一种写法：
    //    lifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
    //        collect {
    //            action(it)
    //        }
    //    }
}

/**
 * MVI模式中使用
 */
inline fun <T, A> Flow<T>.flowWithLifecycle2(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    prop1: KProperty1<T, A>,
    crossinline block: suspend CoroutineScope.(A) -> Unit,
) = lifecycleOwner.lifecycleScope.launch {
    //前后台切换时可以重复订阅数据。如：Lifecycle.State是STARTED，那么在生命周期进入 STARTED 状态时开始任务，在 STOPED 状态时停止订阅
    flowOnSingleLifecycle(lifecycleOwner.lifecycle, minActiveState)
        .map { prop1.get(it) }
        .collect { block(it) }
}

inline fun <T> Flow<T>.flowSingleWithLifecycle2(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.(T) -> Unit,
) = lifecycleOwner.lifecycleScope.launch {
    flowOnSingleLifecycle(lifecycleOwner.lifecycle, minActiveState).collect { block(it) }
}

/**
 * NOTE: 如果不想对UI层的Lifecycle.repeatOnLifecycle/Flow.flowWithLifecycle在前后台切换时重复订阅，可以使用此方法；
 * 效果类似于Channel，不过Channel是一对一的，而这里是一对多的
 */
fun <T> Flow<T>.flowOnSingleLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isFirstCollect: Boolean = true,
): Flow<T> = callbackFlow {
    var lastValue: T? = null
    lifecycle.repeatOnLifecycle(minActiveState) {
        this@flowOnSingleLifecycle.collect {
            if ((lastValue != null || isFirstCollect) && (lastValue != it)) {
                send(it)
            }
            lastValue = it
        }
    }
    lastValue = null
    close()
}
