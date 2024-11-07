package com.almin.arch.ktx

import java.lang.reflect.ParameterizedType

/**
 * Created by Almin on 2024/1/2.
 */
//fun <T> Any.classInstance() : T {
//    val type = javaClass.genericSuperclass
//    val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<T>
//    return clazz.newInstance()
//}

fun <T> Any.clazz(position: Int = 0) : Class<T> {
    val type = javaClass.genericSuperclass
    val clazz = (type as ParameterizedType).actualTypeArguments[position] as Class<T>
    return clazz
}


inline fun <reified T> getGenericTypeClass(): Class<T> = T::class.java

inline fun <reified T> classInstance() : T {
    val clazz: Class<T> = getGenericTypeClass<T>()
    return clazz.newInstance()
}
