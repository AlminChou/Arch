package com.almin.arch.network.exception

/**
 * Created by Almin on 2023/10/13.
 */
class ApiException : Exception {
    var code: Int = 0

    constructor() : super()
    constructor(code: Int, message: String?) : super(message) {
        this.code = code
    }

    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)

    override fun toString(): String {
        return "[${super.toString()}]\n[code=$code,message=${message}]\n[cause=${cause}]"
    }
}