package com.almin.arch.ui.widget

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner


/**
 * Created by Almin on 2023/12/29.
 */
interface SuperAbilityView<VD: ViewDelegate> : DefaultLifecycleObserver {

    fun viewDelegate() : VD?

    fun createViewDelegate(viewModelStoreOwner: ViewModelStoreOwner) : VD

    fun viewLifeCycle() : Lifecycle?

    fun viewLifeCycleOwner(): LifecycleOwner?

    fun initData(viewLifecycleOwner: LifecycleOwner, viewDelegate: VD)

}