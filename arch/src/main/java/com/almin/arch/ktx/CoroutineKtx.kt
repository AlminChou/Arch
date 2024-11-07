package com.almin.arch.ktx

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Almin on 2024/9/23.
 */
suspend fun <T> CoroutineScope.main(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.Main) {
    block()
}

suspend fun <T> CoroutineScope.io(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.IO) {
    block()
}