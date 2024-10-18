package com.example.data.network.retrofit.factory

import com.example.common.network.ResultWrapper
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResponseAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {

        if (Call::class.java != getRawType(returnType)) return null
        check(returnType is ParameterizedType)

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != ResultWrapper::class.java) return null
        check(responseType is ParameterizedType)


        val successType = getParameterUpperBound(0, responseType)

        return ResponseAdapter<Any>(successType)
    }
}