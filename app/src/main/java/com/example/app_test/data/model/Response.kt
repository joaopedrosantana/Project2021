package com.example.app_test.data.model

sealed class Response<T>
data class SuccessResponse<T>(val body: T) : Response<T>()
data class ErrorResponse<T>(val error: RequestError) : Response<T>()

data class RequestError(
    val status: Int,
    val message: String
)