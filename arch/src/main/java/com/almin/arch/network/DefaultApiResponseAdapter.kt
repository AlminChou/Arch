package com.almin.arch.network

import com.almin.arch.network.exception.ApiException
import com.almin.arch.network.ApiResponseAdapter

/**
 * Created by Almin on 2023/12/6.
 */
class DefaultApiResponseAdapter : ApiResponseAdapter {

    override fun <T> getResponseBody(response: SuperResponse<T>): T? {
        if(response.isSuccess()) {
            return response.getBody()
        }

        throw ApiException(response.getCode(), response.getMessage())
    }

}