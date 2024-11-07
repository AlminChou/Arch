package com.almin.arch.log

import android.util.Log
import com.almin.arch.log.AndroidLogPrinter

/**
 * Created by Almin on 2023/12/29.
 */
object LogUtil {

    internal var consolePrinter: LogPrinter? = AndroidLogPrinter()

    private var filePrinter: LogPrinter? = null

    // 是否启动控制台打印
    var consolePrinterEnabled: Boolean = true

    // 是否启动文件打印
    var filePrinterEnabled: Boolean = true

    /**
     * 设置文件打印
     */
    fun setFilePrinter(filePrinter: LogPrinter) {
        this.filePrinter = filePrinter
    }

    fun setConsolePrinter(printer: LogPrinter) {
        this.consolePrinter = printer
    }

    fun e(tag: String, message: String, filePrinter: Boolean = false) {
        log(Log.ERROR, tag, message, filePrinter)
    }

    fun e(tag: String, throwable: Throwable, filePrinter: Boolean = false) {
        val cause = Log.getStackTraceString(throwable)
        if (cause.isEmpty()) {
            return
        }
        e(tag, cause, filePrinter)
    }

    fun e(tag: String, message: String?, throwable: Throwable, filePrinter: Boolean = false) {
        val cause = Log.getStackTraceString(throwable)
        if (message == null && cause.isEmpty()) {
            return
        }
        e(tag, message + "\t\t" + cause, filePrinter)
    }

    fun d(tag: String, message: String, filePrinter: Boolean = false) {
        log(Log.DEBUG, tag, message, filePrinter)
    }

    fun i(tag: String, message: String, filePrinter: Boolean = false) {
        log(Log.INFO, tag, message, filePrinter)
    }

    fun v(tag: String, message: String, filePrinter: Boolean = false) {
        log(Log.VERBOSE, tag, message, filePrinter)
    }

    fun w(tag: String, message: String, filePrinter: Boolean = false) {
        log(Log.WARN, tag, message, filePrinter)
    }

    /**
     * 输出日志
     */
    fun log(level: Int = Log.INFO, tag: String?, message: String?, filePrinter: Boolean = false) {
        if (tag.isNullOrEmpty()) {
            return
        }
        if (message.isNullOrEmpty()) {
            return
        }
        // 输出控制台
        logConsole(level, tag, message)
        // 输出文件
        if (filePrinter) {
            logFile(level, tag, message)
        }
    }

    /**
     * 输出到控制台
     */
    fun logConsole(level: Int = Log.INFO, tag: String, message: String) {
        if (!consolePrinterEnabled) {
            return
        }
        consolePrinter?.println(level, tag, message)
    }

    /**
     * 输出到文件
     */
    fun logFile(level: Int = Log.INFO, tag: String, message: String) {
        if (!filePrinterEnabled) {
            return
        }
        filePrinter?.println(level, tag, message)
    }
}