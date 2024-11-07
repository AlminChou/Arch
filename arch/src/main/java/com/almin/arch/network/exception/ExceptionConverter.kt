package com.almin.arch.network.exception

/**
 * Created by Almin on 2023/12/6.
 */
interface ExceptionConverter {
    fun convert(throwable: Throwable): Throwable
}