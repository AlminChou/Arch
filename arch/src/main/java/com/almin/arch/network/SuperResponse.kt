package com.almin.arch.network

import java.io.Serializable

/**
 * Created by Almin on 2023/12/6.
 */
interface SuperResponse<T> : Serializable  {

    fun isSuccess() : Boolean

    /**
     * 真实有效的结果（去除了基础属性后的数据）
     */
    fun getBody(): T?

    fun setBody(body: T?)

    /**
     * 获取响应码
     */
    fun getCode(): Int

    /**
     * 获取消息体
     */
    fun getMessage(): String?
}