package com.almin.arch.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.almin.arch.ktx.clazz
import com.almin.arch.log.ILog

/**
 * Created by Almin on 2023/12/29.
 */
abstract class SuperContainerView<VD : ViewDelegate, VB : ViewBinding> : FrameLayout,
    SuperAbilityView<VD>, ILog {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private var viewDelegate: VD? = null
        private set

    protected lateinit var viewBinding: VB

    override fun createViewDelegate(viewModelStoreOwner: ViewModelStoreOwner): VD {
        return ViewModelProvider(viewModelStoreOwner).get(clazz<VD>())
    }

    override fun viewDelegate(): VD? = viewDelegate

    private var viewLifeCycleOwner: LifecycleOwner? = null

    open protected fun init(context: Context) {
        val vbClass = clazz<VB>(1)
        val method = vbClass.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        viewBinding = method.invoke(null, LayoutInflater.from(context), this, true) as VB
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        findViewTreeViewModelStoreOwner()?.run {
            if (viewDelegate == null) {
                viewDelegate = createViewDelegate(this)
            }
        }

        viewLifeCycle()?.addObserver(this)
        viewLifeCycleOwner()?.run {
            viewDelegate?.let {
                initData(this, it)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // remove view 触发
        viewLifeCycleOwner()?.run {
            onDestroy(this)
        }
        viewLifeCycle()?.removeObserver(this)
        viewDelegate?.clear()
        viewDelegate = null
        viewLifeCycleOwner = null
    }

    // 页面生命周期会触发, 上面手动触发一下 removeview时候
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
    }

    override fun viewLifeCycle(): Lifecycle? {
        return viewLifeCycleOwner()?.lifecycle
    }

    override fun viewLifeCycleOwner(): LifecycleOwner? {
        if(viewLifeCycleOwner == null) {
            viewLifeCycleOwner = findViewTreeLifecycleOwner()
        }
        return viewLifeCycleOwner
    }
}