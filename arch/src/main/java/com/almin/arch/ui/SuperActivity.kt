package com.almin.arch.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Almin on 2023/12/6.
 */
open abstract class SuperActivity : AppCompatActivity() {

    abstract fun getLayoutResId() : Int

    protected fun initBefore() {
        onCreateView()
    }
    protected abstract fun initView()
    protected abstract fun initData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBefore()
        initView()
        initData()
    }



    /**
     * 创建视图
     */
    protected open fun onCreateView() {
        val layoutId = getLayoutResId()
        if (layoutId != View.NO_ID) {
            setContentView(layoutId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}