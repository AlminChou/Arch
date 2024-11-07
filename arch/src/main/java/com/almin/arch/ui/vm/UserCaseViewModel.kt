package com.almin.arch.ui.vm

import androidx.lifecycle.ViewModelStoreOwner
import com.almin.arch.ktx.classInstance
import com.almin.arch.ktx.useCaseViewModel
import com.almin.arch.ui.usecase.UserCase

/**
 * Created by Almin on 2024/1/12.
 * Desc: 拥有且管理 各种业务useCase的root viewModel。 用于复杂场景抽离业务代码解耦
 */
abstract class UserCaseViewModel : SuperViewModel() {

    val userCaseMap: MutableMap<String, UserCase<*>> by lazy { mutableMapOf() }

    var viewModelStoreOwner: ViewModelStoreOwner? = null
        internal set

    inline fun <reified T : UserCase<*>> provide(): T? {
        try {
            val uc = userCaseMap.getOrPut(T::class.java.simpleName) {
                classInstance<T>()
            } as T?
            uc?.run {
                viewModelStoreOwner?.useCaseViewModel(getViewModelClass())?.let { setUpVm(it) }
                setUpParentVm(this@UserCaseViewModel)
            }
            return uc
        } catch (e: Exception) {
            logE(this::class.java.simpleName, "useCase provide error ${e.message}")
        }

        return null
    }

    override fun onCleared() {
        super.onCleared()
        userCaseMap.forEach { it.value.onCleared() }
        viewModelStoreOwner = null
    }
}



