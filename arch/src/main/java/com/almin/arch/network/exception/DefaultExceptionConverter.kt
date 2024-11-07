package com.almin.arch.network.exception

/**
 * Created by Almin on 2023/12/6.
 */
class DefaultExceptionConverter : ExceptionConverter {
    override fun convert(throwable: Throwable): Throwable {
        return throwable
    }
}