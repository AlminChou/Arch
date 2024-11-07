package com.almin.arch.ui.usecase

import com.almin.arch.ktx.clazz
import com.almin.arch.ui.vm.SuperViewModel
import com.almin.arch.ui.vm.UserCaseViewModel
import java.lang.ref.WeakReference

/**
 * Created by Almin on 2024/1/12.
 */
abstract class UserCase<VM: SuperViewModel> {

    protected lateinit var viewModel: VM
    protected lateinit var parentViewModel: WeakReference<UserCaseViewModel>

    fun setUpVm(vm: SuperViewModel) {
        viewModel = vm as VM
    }

    fun setUpParentVm(vm: UserCaseViewModel) {
        parentViewModel = WeakReference(vm)
    }

    fun getViewModelClass() = clazz<VM>().kotlin

    open fun onCleared() {
        viewModel.clear()
        parentViewModel.clear()
    }
}