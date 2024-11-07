package com.almin.arch.ktx

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import kotlin.reflect.KClass

/**
 * Created by Almin on 2024/7/25.
 * Desc: UseCase 相关依赖扩展方法 涉及viewModel注入与store作用域
 */

fun <T : ViewModel> ViewModelStoreOwner.useCaseViewModel(modelClass: KClass<T>?): T? {
    modelClass?.run {
        return this@useCaseViewModel.create(modelClass = this)?.value
    }

    return null
}

public inline fun <reified T : ViewModel> ViewModelStoreOwner.useCaseViewModel(): T? {
    return create(modelClass = getGenericTypeClass<T>().kotlin)?.value
}


@MainThread
public fun <VM : ViewModel> ViewModelStoreOwner.create(
    modelClass: KClass<VM>,
    extrasProducer: (() -> CreationExtras)? = null,
    ownerProducer: () -> ViewModelStoreOwner = { this },
    factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM>? {
    return when (this) {
        is ComponentActivity -> {
            val factoryPromise = factoryProducer ?: {
                defaultViewModelProviderFactory
            }

            return ViewModelLazyWithStore(
                this,
                modelClass,
                { viewModelStore },
                factoryPromise,
                { extrasProducer?.invoke() ?: this.defaultViewModelCreationExtras }
            )
        }

        is Fragment -> {
            this.createViewModelLazyWithStore(
                viewModelStoreOwner = this,
                modelClass,
                { ownerProducer().viewModelStore },
                factoryProducer
            )
        }

        else -> {
            null
        }
    }
}


@MainThread
public inline fun <reified VM : ViewModel> ComponentActivity.useCaseViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }

    return ViewModelLazyWithStore(
        this,
        VM::class,
        { viewModelStore },
        factoryPromise,
        { this.defaultViewModelCreationExtras }
    )
}

@MainThread
public inline fun <reified VM : ViewModel> ComponentActivity.useCaseViewModels(
    noinline extrasProducer: (() -> CreationExtras)? = null,
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }

    return ViewModelLazyWithStore(
        this,
        VM::class,
        { viewModelStore },
        factoryPromise,
        { extrasProducer?.invoke() ?: this.defaultViewModelCreationExtras }
    )
}

@MainThread
public inline fun <reified VM : ViewModel> Fragment.useCaseViewModels(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> = createViewModelLazyWithStore(
    viewModelStoreOwner = this,
    VM::class,
    { ownerProducer().viewModelStore },
    factoryProducer
)


@MainThread
public inline fun <reified VM : ViewModel> Fragment.activityUseCaseViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> = createViewModelLazyWithStore(
    viewModelStoreOwner = requireActivity(),
    VM::class, { requireActivity().viewModelStore },
    factoryProducer ?: { requireActivity().defaultViewModelProviderFactory }
)