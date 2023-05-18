package com.project.currencyapp.data.source.network

import androidx.core.util.Preconditions
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.project.currencyapp.domain.models.common.ErrorResponse
import com.project.currencyapp.domain.models.common.ResponseWrapper
import com.project.currencyapp.presentation.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}


private val interceptor = HttpLoggingInterceptor()

private val client by lazy {

    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    val clientBuilder = OkHttpClient.Builder()
    clientBuilder.addInterceptor(interceptor)
    clientBuilder.addInterceptor { chain ->
        val request: Request =
            chain.request().newBuilder().addHeader("apikey", Constants.API_KEY).build()
        chain.proceed(request)
    }

    clientBuilder.build()
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> Deferred<T>
): ResponseWrapper<T> {
    return withContext(dispatcher) {
        try {
            val response = apiCall.invoke().await()
            ResponseWrapper.Success(response)
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResponseWrapper.GenericError(code, errorResponse)
                }
                is IOException -> ResponseWrapper.NetworkError
                else -> {
                    ResponseWrapper.GenericError(null, null)
                }
            }
        }
    }
}

fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.let {
            val gson = Gson()
            val error: ErrorResponse = gson.fromJson(
                it.string(),
                ErrorResponse::class.java
            )
            error
        }

    } catch (exception: Exception) {
        null
    }
}
