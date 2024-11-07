package com.almin.arch.log

import android.util.Log

/**
 * Created by Almin on 2022/12/29 19:12
 * Desc:Android日志
 */
class AndroidLogPrinter : LogPrinter {
    override fun println(level: Int, tag: String, message: String) {
        Log.println(level, tag, message)
    }
}