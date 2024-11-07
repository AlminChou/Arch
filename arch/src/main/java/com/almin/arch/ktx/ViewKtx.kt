package com.almin.arch.ktx

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2

/**
 * Created by Almin on 2023/12/26.
 */
fun ViewPager2.currentFragment(fragmentManager: FragmentManager) : Fragment? {
    return fragmentManager.findFragmentByTag("${adapter?.getItemId(currentItem)}")
}

fun ViewPager2.getFragment(fragmentManager: FragmentManager, position: Int) : Fragment? {
    return fragmentManager.findFragmentByTag("${adapter?.getItemId(position)}")
}