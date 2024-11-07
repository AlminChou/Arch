package com.almin.arch.ui.engine

import androidx.lifecycle.MutableLiveData
import com.almin.arch.network.SuperResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.CoroutineContext

/**
 * Created by Almin on 2023/12/29.
 */
interface AbilityEngine {

    fun injectScope(scope: CoroutineScope)

    fun coroutineScope(): CoroutineScope?

    fun <T> launchF(
        context: CoroutineContext = Dispatchers.Main,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        callBack: FlowDsl<T>.() -> Unit
    ): Job


    fun <T> launch(
        context: CoroutineContext = Dispatchers.Main,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        callBack: SuspendDsl<T>.() -> Unit
    ): Job

    fun <T> quickLaunch(
        context: CoroutineContext = Dispatchers.Main,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        callBack: SuspendDsl<SuperResponse<T>>.() -> Unit
    ): Job

    fun <T> quickLaunch(
        liveData: MutableLiveData<T>,
        context: CoroutineContext = Dispatchers.Main,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        callBack: SuspendDsl<SuperResponse<T>>.() -> Unit
    ): Job


    fun <T> quickLaunch(
        stateFlow: MutableStateFlow<T?>,
        context: CoroutineContext = Dispatchers.Main,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        callBack: SuspendDsl<SuperResponse<T>>.() -> Unit
    ): Job


    abstract class DslCallback<Model> {
        internal var prepare: (() -> Unit)? = null
        internal var transform: ((model: Model) -> Model)? = null
        internal var success: ((model: Model) -> Unit)? = null
        internal var failed: ((error: Throwable) -> Unit)? = null
        internal var final: (() -> Unit)? = null

        fun prepare(function: () -> Unit) {
            this.prepare = function
        }

        fun success(function: (model: Model) -> Unit) {
            this.success = function
        }

        fun transform(function: (model: Model) -> Model) {
            this.transform = function
        }

        fun failed(function: (error: Throwable) -> Unit) {
            this.failed = function
        }

        fun final(function: () -> Unit) {
            this.final = function
        }
    }

    class SuspendDsl<Model> : DslCallback<Model>() {
        internal lateinit var call: suspend (() -> Model)

        fun call(function: suspend () -> Model) {
            this.call = function
        }
    }

    class FlowDsl<Model> : DslCallback<Model>() {
        internal lateinit var call: suspend () -> Flow<Model>

        fun call(function: suspend () -> Flow<Model>) {
            this.call = function
        }
    }
}