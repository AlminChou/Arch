package com.almin.arch.ui

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.almin.arch.ui.SuperActivity

/**
 * Created by Almin on 2023/12/6.
 */
abstract class SuperBindingActivity<VB: ViewBinding>(
    private val inflate: (LayoutInflater) -> VB
)  : SuperActivity() {

    protected lateinit var binding: VB

    override fun onCreateView() {
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun getLayoutResId(): Int {
        return View.NO_ID
    }
}