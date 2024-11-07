package com.almin.arch.log

/**
 * Created by Almin on 2023/12/29.
 */
interface LogPrinter {
    fun println(level: Int, tag: String, message: String)
}