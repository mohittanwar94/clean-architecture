package com.example.data.network.retrofit.factory

import com.example.common.network.ResultWrapper
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ResponseAdapter<T>(
    private val successType : Type
) : CallAdapter<T, Call<ResultWrapper<T>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<ResultWrapper<T>> = ResponseCall(call)
}