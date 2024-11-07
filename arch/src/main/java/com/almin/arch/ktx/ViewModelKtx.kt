package com.almin.arch.ktx

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.almin.arch.ui.vm.UserCaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KClass

/**
 * Created by Almin on 2022/6/22.
 */


fun <T : ViewModel> ViewModelStoreOwner.viewModel(): T {
    return ViewModelProvider(this).get(clazz<T>())
}

fun <T : ViewModel> ViewModelStoreOwner.viewModel(modelClass: Class<T>): T {
    return ViewModelProvider(this).get(modelClass)
}


/**
 * Helper method for creation of [ViewModelLazy], that resolves `null` passed as [factoryProducer]
 * to default factory.
 */
@MainThread
public fun <VM : ViewModel> Fragment.createViewModelLazyWithStore(
    viewModelStoreOwner: ViewModelStoreOwner = this,
    viewModelClass: KClass<VM>,
    storeProducer: () -> ViewModelStore,
    factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }
    return ViewModelLazyWithStore(
        viewModelStoreOwner,
        viewModelClass,
        storeProducer,
        factoryPromise
    )
}

public class ViewModelLazyWithStore<VM : ViewModel> @JvmOverloads constructor(
    private val viewModelStoreOwner: ViewModelStoreOwner,
    private val viewModelClass: KClass<VM>,
    private val storeProducer: () -> ViewModelStore,
    private val factoryProducer: () -> ViewModelProvider.Factory,
    private val extrasProducer: () -> CreationExtras = { CreationExtras.Empty }
) : Lazy<VM> {
    private var cached: VM? = null

    override val value: VM
        get() {
            val viewModel = cached
            return if (viewModel == null) {
                val factory = factoryProducer()
                val store = storeProducer()
                ViewModelProvider(
                    store,
                    factory,
                    extrasProducer()
                ).get(viewModelClass.java).also {
                    cached = it
                    if (it is UserCaseViewModel) {
                        it.viewModelStoreOwner = viewModelStoreOwner
                    }
                }
            } else {
                viewModel
            }
        }

    override fun isInitialized(): Boolean = cached != null
}


fun ViewModel.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return viewModelScope.launch(context, start, block)
}

fun <T> ViewModel.async(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return viewModelScope.async(context, start, block)
}