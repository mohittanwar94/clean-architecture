package com.example.data.network.retrofit.factory

import com.example.common.network.ResultWrapper
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.UnsupportedOperationException

internal class ResponseCall<T> constructor(
    private val callDelegate: Call<T>
) : Call<ResultWrapper<T>> {

    override fun enqueue(callback: Callback<ResultWrapper<T>>) = callDelegate.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.body()?.let {
                when(response.code()) {
                    in 200..208 -> {
                        callback.onResponse(this@ResponseCall, Response.success(ResultWrapper.Success(it)))
                    }
                    in 400..409 -> {
                        callback.onResponse(this@ResponseCall, Response.success(ResultWrapper.Error(response.message())))
                    }
                }
            }?: callback.onResponse(this@ResponseCall, Response.success(ResultWrapper.Error("NullResult")))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            callback.onResponse(this@ResponseCall, Response.success(ResultWrapper.Error("unable to connect")))
            call.cancel()
        }
    })

    override fun clone(): Call<ResultWrapper<T>> = ResponseCall(callDelegate.clone())

    override fun execute(): Response<ResultWrapper<T>> = throw UnsupportedOperationException("ResponseCall does not support execute.")

    override fun isExecuted(): Boolean = callDelegate.isExecuted

    override fun cancel() = callDelegate.cancel()

    override fun isCanceled(): Boolean = callDelegate.isCanceled

    override fun request(): Request = callDelegate.request()

    override fun timeout(): Timeout = callDelegate.timeout()
}