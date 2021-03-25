package com.sf.architecture.shared

sealed class Response<out Success> {
    data class Success<out T>(val value: T) : Response<T>()
    data class Failure(val reason: Throwable) : Response<Nothing>()
}