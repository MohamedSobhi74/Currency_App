package com.project.currencyapp.domain.models.common

sealed class ResponseWrapper<out T> {

    data class Success<out T>(val value: T) : ResponseWrapper<T>()

    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) :
        ResponseWrapper<Nothing>()

    data class NetworkError(val message: String) : ResponseWrapper<Nothing>()
}