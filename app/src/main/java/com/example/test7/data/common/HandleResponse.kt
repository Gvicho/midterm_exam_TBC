package com.example.test7.data.common

import android.util.Log.d
import com.example.test6.data.common.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class HandleResponse @Inject constructor() {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Flow<ResultWrapper<T>> = flow {
        try {
            d("tag123","try start loading true")
            emit(ResultWrapper.Loading(loading = true))
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                d("tag123","try Succesfull")
                emit(ResultWrapper.Success(data = body))
            } else {
                d("tag123","try error ${response.errorBody()}}")
                emit(ResultWrapper.Error(errorMessage = response.errorBody()?.string() ?: ""))
            }
        } catch (e: Throwable) {
            d("tag123","try vatch error $e")
            emit(ResultWrapper.Error(errorMessage = e.message ?: ""))
        } finally {
            emit(ResultWrapper.Loading(loading = false))
        }
    }
}