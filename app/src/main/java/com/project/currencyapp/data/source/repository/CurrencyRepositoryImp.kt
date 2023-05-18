package com.project.currencyapp.data.source.repository

import com.project.currencyapp.data.source.network.CurrencyAPIs
import com.project.currencyapp.data.source.network.currencyAPIs
import com.project.currencyapp.data.source.network.safeApiCall
import com.project.currencyapp.domain.models.CurrencySymbolsResponse
import com.project.currencyapp.domain.models.common.ResponseWrapper
import com.project.currencyapp.domain.repository.CurrencyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

val currencyRepository: CurrencyRepository by lazy {
    CurrencyRepositoryImp(currencyAPIs = currencyAPIs)
}

class CurrencyRepositoryImp(
    val currencyAPIs: CurrencyAPIs,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CurrencyRepository {

    override suspend fun getCurrencySymbols(): ResponseWrapper<CurrencySymbolsResponse> {

        return withContext(dispatcher) {
            val response = safeApiCall {
                currencyAPIs.getCurrencySymbols()
            }
            when (response) {
                is ResponseWrapper.Success -> {
                    return@withContext ResponseWrapper.Success(response.value)
                }
                is ResponseWrapper.GenericError -> return@withContext ResponseWrapper.GenericError(
                    response.code,
                    response.error
                )
                else -> return@withContext ResponseWrapper.NetworkError
            }
        }
    }
}