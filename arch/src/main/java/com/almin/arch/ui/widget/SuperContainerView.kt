package com.almin.arch.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
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
    SuperAbilityView<VD>, LifecycleOwner, ILog {

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
    // view tree 的生命周期， 如果view跟随页面销毁可以用这个owner 重写下
//    private val viewTreeLifeCycleOwner: LifecycleOwner? get() = findViewTreeLifecycleOwner()
    // view 自身生命周期
    private val lifecycleRegistry = LifecycleRegistry(this)
    override val lifecycle: Lifecycle get() = lifecycleRegistry

    @CallSuper
    protected open fun init(context: Context) {
        val vbClass = clazz<VB>(1)
        val method = vbClass.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        viewBinding = method.invoke(null, LayoutInflater.from(context), this, true) as VB
        logI("lifecycle ->  init  currentState -> INITIALIZED  ${this}")
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        logI("lifecycle ->  onAttachedToWindow  currentState -> CREATED  ${this}")
        viewLifeCycle()?.addObserver(this)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED

        findViewTreeViewModelStoreOwner()?.run {
            if (viewDelegate == null) {
                viewDelegate = createViewDelegate(this)
            }
        }

        viewDelegate?.let {
            logI("lifecycle ->  onAttachedToWindow  init data  ${this}")
            initData(this, it)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        logI("lifecycle ->  onDetachedFromWindow    currentState -> DESTROYED   ${this}")
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        viewLifeCycle()?.removeObserver(this)
    }

    // 页面生命周期会触发, 上面手动触发一下 removeview时候
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        logI("lifecycle ->  onDestroy  ")
        viewDelegate?.clear()
        viewDelegate = null
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        logI("lifecycle ->  onWindowVisibilityChanged  ${visibility == VISIBLE}  ${this}")

        if (visibility == VISIBLE) {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        } else {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }
    }

    override fun viewLifeCycle(): Lifecycle? {
        return viewLifeCycleOwner()?.lifecycle
    }

    override fun viewLifeCycleOwner(): LifecycleOwner? {
        return this
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        logI("lifecycle call ->  onCreate  ${this}")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        logI("lifecycle call ->  onPause  ${this}")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        logI("lifecycle call ->  onResume  ${this}")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        logI("lifecycle call ->  onStop  ${this}")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        logI("lifecycle call ->  onStart  ${this}")
    }
}