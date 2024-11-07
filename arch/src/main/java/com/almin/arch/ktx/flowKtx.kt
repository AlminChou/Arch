package com.almin.arch.ktx

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import com.almin.arch.ui.data.LoadStatus
import com.almin.arch.ui.data.UiData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Almin on 2023/7/5.
 */

fun <T> Flow<T>.collect(
    lifecycle: Lifecycle?,
    state: Lifecycle.State = Lifecycle.State.CREATED,
    action: suspend CoroutineScope.(T) -> Unit
) {
    lifecycle?.coroutineScope?.launch {
        lifecycle.repeatOnLifecycle(state) {
            collect {
                action(it)
            }
        }
    }
}

fun <T> Flow<T>.collectLatest(
    lifecycle: Lifecycle?,
    state: Lifecycle.State = Lifecycle.State.CREATED,
    action: suspend CoroutineScope.(T) -> Unit
) {
    lifecycle?.coroutineScope?.launch {
        lifecycle.repeatOnLifecycle(state) {
            collectLatest {
                action(it)
            }
        }
    }
}
fun <T> Flow<T>.collect(
    lifecycleOwner: LifecycleOwner?,
    state: Lifecycle.State = Lifecycle.State.CREATED,
    action: suspend CoroutineScope.(T) -> Unit
) {
    collect(lifecycleOwner?.lifecycle, state, action)
}

fun <T> Flow<T>.collectLatest(
    lifecycleOwner: LifecycleOwner?,
    state: Lifecycle.State = Lifecycle.State.CREATED,
    action: suspend CoroutineScope.(T) -> Unit
) {
    collectLatest(lifecycleOwner?.lifecycle, state, action)
}

// update value
fun <T> MutableStateFlow<UiData<T>>.updateValue(action: UiData<T>.() -> UiData<T>) {
    value = value.action()
}

fun <T> MutableStateFlow<UiData<T>>.empty(data: T?) {
    value = value.copy(loadStatus = LoadStatus.Empty, value = data, throwable = null, message = null)
}

fun <T> MutableStateFlow<UiData<T>>.finish(
    refresh: Boolean = false,
    loadMore: Boolean = false,
    data: T?
) {
    value = value.copy(loadStatus = LoadStatus.finish(refresh, loadMore), value = data, throwable = null, message = null)
}

fun <T> MutableStateFlow<UiData<T>>.failed(
    refresh: Boolean = false,
    loadMore: Boolean = false,
    message: String?,
    throwable: Throwable? = null
) {
    value = value.copy(loadStatus = LoadStatus.failed(refresh, loadMore), message = message, throwable = throwable)
}

// loading状态 数据null
fun <T> MutableStateFlow<UiData<T>>.loading(refresh: Boolean = false, loadMore: Boolean = false) {
    value = value.copy(loadStatus = LoadStatus.loading(refresh, loadMore), throwable = null, message = null)
}
// 同上， 刷新和loadMore 必定2选1
fun <T> MutableStateFlow<UiData<T>>.refreshOrMoreLoading(refreshOrLoadMore: Boolean) {
    value = value.copy(loadStatus = LoadStatus.loading(refreshOrLoadMore, !refreshOrLoadMore), throwable = null, message = null)
}

// loading 状态 带数据，可能是默认数据 或者缓存
fun <T> MutableStateFlow<UiData<T>>.loadingWithValue(
    refresh: Boolean = false,
    loadMore: Boolean = false,
    action: UiData<T>.() -> UiData<T>
) {
    value = value.copy(loadStatus = LoadStatus.loading(refresh, loadMore), throwable = null, message = null).action()
}