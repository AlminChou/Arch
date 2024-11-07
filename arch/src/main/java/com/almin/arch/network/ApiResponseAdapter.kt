package com.almin.arch.network


/**
 * Created by Almin on 2023/12/6.
 */
interface ApiResponseAdapter {

    /**
     * 获取接口响应体
     */
    fun <T> getResponseBody(response: SuperResponse<T>): T?
}