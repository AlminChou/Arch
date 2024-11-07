package com.almin.arch.ktx

import com.almin.arch.network.RetrofitClientProvider
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody

/**
 * Created by Almin on 2024/9/19.
 */
fun String.toRequestBody() : RequestBody {
    return RetrofitClientProvider.createCustomJsonRequestBody(this)
}

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()) // 反射支持
    .build()

// 将 JSON 字符串转换为对象
inline fun <reified T> String.fromJson(): T? {
    try {
        val jsonAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return jsonAdapter.fromJson(this)
    } catch (e: Exception) {
        return null
    }
}

// 将对象转换为 JSON 字符串
inline fun <reified T> T.toJson(): String? {
    try {
        val jsonAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return jsonAdapter.toJson(this)
    } catch (e: Exception) {
        return null
    }
}