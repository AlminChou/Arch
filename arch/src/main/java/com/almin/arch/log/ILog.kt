package com.almin.arch.log

/**
 * Created by Almin on 2023/12/28.
 */
interface ILog  {

    /**
     * 默认日志Tag
     */
    fun getLogTag(): String {
        return this::class.java.simpleName + "-ILog"
    }

    fun logI(message: String, tag: String = getLogTag(), filePrinter: Boolean = false) {
        LogUtil.i(tag, message, filePrinter)
    }

    fun logV(message: String, tag: String = getLogTag(), filePrinter: Boolean = false) {
        LogUtil.v(tag, message, filePrinter)
    }

    fun logW(message: String, tag: String = getLogTag(), filePrinter: Boolean = false) {
        LogUtil.w(tag, message, filePrinter)
    }

    fun logD(message: String, tag: String = getLogTag(), filePrinter: Boolean = false) {
        LogUtil.d(tag, message, filePrinter)
    }

    fun logE(message: String, tag: String = getLogTag(), filePrinter: Boolean = false) {
        LogUtil.e(tag, message, filePrinter)
    }

    fun logE(
        throwable: Throwable,
        tag: String = getLogTag(),
        filePrinter: Boolean = false
    ) {
        LogUtil.e(tag, throwable, filePrinter)
    }

    fun logE(
        message: String,
        throwable: Throwable,
        tag: String = getLogTag(),
        filePrinter: Boolean = false
    ) {
        LogUtil.e(tag, message, throwable, filePrinter)
    }
}