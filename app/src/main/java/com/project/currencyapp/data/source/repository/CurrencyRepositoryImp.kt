package com.project.currencyapp.data.source.repository

import com.project.currencyapp.data.source.network.CurrencyAPIs
import com.project.currencyapp.data.source.network.safeApiCall
import com.project.currencyapp.domain.models.CurrencySymbolsResponse
import com.project.currencyapp.domain.models.common.ResponseWrapper
import com.project.currencyapp.domain.models.convert.CurrencyConvertResponse
import com.project.currencyapp.domain.repository.CurrencyRepository
import com.project.currencyapp.presentation.CurrencyApplication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRepositoryImp @Inject constructor(
    val currencyAPIs: CurrencyAPIs,
    val app:CurrencyApplication,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CurrencyRepository {

    override suspend fun getCurrencySymbols(): ResponseWrapper<CurrencySymbolsResponse> {

        return withContext(dispatcher) {
            val response = safeApiCall {
                currencyAPIs.getCurrencySymbols()
            }
            when (response) {
                is ResponseWrapper.Success -> return@withContext ResponseWrapper.Success(response.value)
                else -> return@withContext response
            }
        }
    }

    override suspend fun convertCurrency(
        from: String,
        to: String,
        amount: Int
    ): ResponseWrapper<CurrencyConvertResponse> {
        return withContext(dispatcher) {
            val response = safeApiCall {
                currencyAPIs.convertCurreny(from, to, amount)
            }
            when (response) {
                is ResponseWrapper.Success -> return@withContext ResponseWrapper.Success(response.value)
                else -> return@withContext response
            }
        }
    }
}