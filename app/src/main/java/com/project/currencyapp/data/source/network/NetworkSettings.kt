package com.project.currencyapp.data.source.network

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.project.currencyapp.domain.models.common.ErrorResponse
import com.project.currencyapp.domain.models.common.ResponseWrapper
import com.project.currencyapp.presentation.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

// retrofit initialization on demand by lazy
val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}


// log interceptor initialization for api tracking log
private val interceptor = HttpLoggingInterceptor()

// OkHttpClient initialization on demand by lazy
private val client by lazy {

    // tell logging interceptor to log apis body
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    val clientBuilder = OkHttpClient.Builder()
    clientBuilder.connectTimeout(15, TimeUnit.SECONDS) // request connect timeout
    clientBuilder.readTimeout(15, TimeUnit.SECONDS)  // socket timeout

    // add logging interceptor to client
    clientBuilder.addInterceptor(interceptor)

    // add api key to header using interceptor with client
    clientBuilder.addInterceptor { chain ->
        val request: Request =
            chain.request().newBuilder().addHeader("apikey", Constants.API_KEY).build()
        chain.proceed(request)
    }

    clientBuilder.build()
}

// general api call for all apis
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
                is IOException -> ResponseWrapper.NetworkError(
                    throwable.message ?: "Something went wrong."
                )
                else -> {
                    ResponseWrapper.GenericError(null, null)
                }
            }
        }
    }
}

// convert throwable to readable format
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
