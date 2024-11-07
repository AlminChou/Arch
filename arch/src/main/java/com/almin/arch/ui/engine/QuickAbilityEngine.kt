package com.almin.arch.ui.engine

import androidx.lifecycle.MutableLiveData
import com.almin.arch.network.SuperResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Almin on 2023/12/29.
 * Desc: 基本协程flow 引擎
 */
class QuickAbilityEngine : AbilityEngine {
    private lateinit var scope: CoroutineScope

    override fun injectScope(scope: CoroutineScope) {
        this.scope = scope
    }

    override fun coroutineScope(): CoroutineScope = scope

    override fun <T> launchF(
        context: CoroutineContext,
        start: CoroutineStart,
        callBack: AbilityEngine.FlowDsl<T>.() -> Unit
    ): Job {
        val dslBlock = AbilityEngine.FlowDsl<T>()
        callBack.invoke(dslBlock)
        return coroutineScope().launch(
            context = context,
            start = start,
            block = {
                dslBlock.run {
                    call.invoke()
                        .onStart {
                            prepare?.invoke()
                        }.onCompletion {
                            final?.invoke()
                        }.catch {
                            failed?.invoke((it))
                        }.collectLatest { result ->
                            success?.invoke(result)
                        }
                }
            })
    }


    override fun <T> launch(
        context: CoroutineContext,
        start: CoroutineStart,
        callBack: AbilityEngine.SuspendDsl<T>.() -> Unit
    ): Job {
        val dslBlock = AbilityEngine.SuspendDsl<T>()
        callBack.invoke(dslBlock)
        return coroutineScope().launch(context = context, start = start) {
            flow {
                emit(dslBlock.call.invoke())
            }.map {
                dslBlock.transform?.invoke(it) ?: it
            }.onStart {
                dslBlock.prepare?.invoke()
            }.catch {
                dslBlock.failed?.invoke((it))
            }.collect {
                dslBlock.success?.invoke(it)
            }
        }
    }

    override fun <T> quickLaunch(
        context: CoroutineContext,
        start: CoroutineStart,
        callBack: AbilityEngine.SuspendDsl<SuperResponse<T>>.() -> Unit
    ): Job {
        val dslBlock = AbilityEngine.SuspendDsl<SuperResponse<T>>()
        callBack.invoke(dslBlock)
        return coroutineScope().launch(context = context, start = start) {
            flow {
                emit(dslBlock.call.invoke())
            }.map {
                dslBlock.transform?.invoke(it) ?: it
            }.onStart {
                dslBlock.prepare?.invoke()
            }.catch {
                dslBlock.failed?.invoke((it))
            }.collect {
                dslBlock.success?.invoke(it)
            }
        }
    }

    override fun <T> quickLaunch(
        liveData: MutableLiveData<T>,
        context: CoroutineContext,
        start: CoroutineStart,
        callBack: AbilityEngine.SuspendDsl<SuperResponse<T>>.() -> Unit
    ): Job {
        val dslBlock = AbilityEngine.SuspendDsl<SuperResponse<T>>()
        callBack.invoke(dslBlock)
        val liveDataValueSet: (T?) -> Unit = {
            if (context == Dispatchers.Main) {
                liveData.value = it
            } else {
                liveData.postValue(it)
            }
        }
        return coroutineScope().launch(context = context, start = start) {
            flow {
                emit(dslBlock.call.invoke())
            }.map {
                dslBlock.transform?.invoke(it) ?: it
            }.onStart {
                dslBlock.prepare?.invoke()
            }.catch {
                dslBlock.failed?.invoke(it)
            }.collect {
                liveDataValueSet(it.getBody())
                dslBlock.success?.invoke(it)
            }
        }
    }


    override fun <T> quickLaunch(
        stateFlow: MutableStateFlow<T?>,
        context: CoroutineContext,
        start: CoroutineStart,
        callBack: AbilityEngine.SuspendDsl<SuperResponse<T>>.() -> Unit
    ): Job {
        val dslBlock = AbilityEngine.SuspendDsl<SuperResponse<T>>()
        callBack.invoke(dslBlock)
        return coroutineScope().launch(context = context, start = start) {
            flow {
                emit(dslBlock.call.invoke())
            }.map {
                dslBlock.transform?.invoke(it) ?: it
            }.onStart {
                dslBlock.prepare?.invoke()
            }.catch {
                dslBlock.failed?.invoke(it)
            }.collect {
                stateFlow.value = it.getBody()
                dslBlock.success?.invoke(it)
            }
        }
    }
}