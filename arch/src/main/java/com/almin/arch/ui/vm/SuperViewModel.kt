package com.almin.arch.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almin.arch.log.ILog
import com.almin.arch.ui.engine.AbilityEngine
import com.almin.arch.ui.engine.QuickAbilityEngine
import kotlinx.coroutines.CoroutineScope

/**
 * Created by Almin on 2023/12/6.
 */
abstract class SuperViewModel(abilityEngine: AbilityEngine = QuickAbilityEngine()) : ViewModel(),
    AbilityEngine by abilityEngine, ILog {

    init {
        abilityEngine.injectScope(getCoroutineScope())
    }

    /**
     * 获取协程作用域
     */
    protected open fun getCoroutineScope(): CoroutineScope {
        return viewModelScope
    }

    open fun clear() {
        onCleared()
    }
}