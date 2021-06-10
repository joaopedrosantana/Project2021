package com.example.app_test.data.remote

import com.example.app_test.data.model.ErrorResponse
import com.example.app_test.data.model.RequestError
import com.example.app_test.data.model.SuccessResponse
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.example.app_test.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    inline fun <reified T> retrofitCreate(okHttpClient: OkHttpClient): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
        return retrofit.create(T::class.java)
    }
}

fun <T> Response<T>.formatResponse(): com.example.app_test.data.model.Response<T> {
    return if (this.isSuccessful) {
        body()?.let {
            SuccessResponse(it)
        } ?: run {
            return ErrorResponse(RequestError(this.code(), this.message()))
        }
    } else {
        val error = this.errorBody()
        val jsonObject = JsonParser.parseString(error?.string()).asJsonObject
        val obj = Gson().fromJson<RequestError>(jsonObject, RequestError::class.java)
        if (obj is RequestError)
            ErrorResponse(obj)
        else {
            val requestErrorGeneric = RequestError(99, "Erro!")
            ErrorResponse(requestErrorGeneric)
        }
    }
}