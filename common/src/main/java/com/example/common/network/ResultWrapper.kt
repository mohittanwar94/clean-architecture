package com.example.common.network

sealed class ResultWrapper<T>(val data: T? = null, val error: String? = null) {

    class Success<T>(data: T) : ResultWrapper<T>(data)
    class Error<T>(msg: String, data: T? = null) : ResultWrapper<T>(data, msg)
    class Loading<T>(data: T? = null) : ResultWrapper<T>(data )
}