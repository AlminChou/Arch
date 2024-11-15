package com.almin.arch.ui.widget

import androidx.annotation.CallSuper
import com.almin.arch.log.ILog
import com.almin.arch.ui.vm.CloseableCoroutineScope
import com.almin.arch.ui.vm.SuperViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren

/**
 * Created by Almin on 2023/12/29.
 * Desc: View层viewModel 承载数据业务逻辑， 作用域与当前UI页面容器的ViewModelStore中
 *       1.由于view的特殊情况可能涉及手动add remove甚至复用机制， 不能依靠原来viewModel的机制来自动销毁协程 所以不能使用viewModelScope协程作用域， 需要自定义协程作用域 手动进行管理
 *       2.因为需要依赖注入， 所以借助viewModel的初始化注入进行获取相关注入能力来获取repository等相关实例
 *       3.注意该ViewDelegate生命周期会跟随页面 viewModel store 不跟随view， 可能view是每次新 但是页面没销毁 重新获取的还是上一个ViewDelegate， 所以注意reset和onclear 操作 区分好clear和onCleared
 */
abstract class ViewDelegate : SuperViewModel(), CoroutineScope by CloseableCoroutineScope (
    SupervisorJob() + Dispatchers.Main.immediate) , ILog {

    override fun getCoroutineScope(): CoroutineScope {
        return this
    }


    /**
     * view 本身生命周期的销毁毁掉
     */
    @CallSuper
    override fun clear() {
        try {
            getCoroutineScope().coroutineContext.cancelChildren()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * viewmodel 跟随页面生命周期的销毁毁掉
     */
    override fun onCleared() {
        super.onCleared()
        try {
            getCoroutineScope().coroutineContext.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}