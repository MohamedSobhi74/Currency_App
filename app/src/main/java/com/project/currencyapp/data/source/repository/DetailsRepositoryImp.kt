package com.project.currencyapp.data.source.repository

import com.project.currencyapp.data.source.network.CurrencyAPIs
import com.project.currencyapp.data.source.network.safeApiCall
import com.project.currencyapp.domain.models.common.ResponseWrapper
import com.project.currencyapp.domain.models.history.HistoryRatesResponse
import com.project.currencyapp.domain.models.latest.LatestRatesResponse
import com.project.currencyapp.domain.repository.DetailsRepository
import com.project.currencyapp.presentation.CurrencyApplication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DetailsRepositoryImp @Inject constructor(
    val currencyAPIs: CurrencyAPIs,
    val app:CurrencyApplication,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DetailsRepository {

    override suspend fun getConvertHistory(
        base: String,
        symbols: String,
        startDate: String,
        endDate: String
    ): ResponseWrapper<HistoryRatesResponse> {

        return withContext(dispatcher) {
            val response = safeApiCall {
                currencyAPIs.getHistory(base, symbols, startDate, endDate)
            }
            when (response) {
                is ResponseWrapper.Success -> return@withContext ResponseWrapper.Success(response.value)
                else -> return@withContext response
            }
        }
    }

    override suspend fun getLatestRates(
        base: String
    ): ResponseWrapper<LatestRatesResponse> {

        return withContext(dispatcher) {
            val response = safeApiCall {
                currencyAPIs.getLatestRates(base)
            }
            when (response) {
                is ResponseWrapper.Success -> return@withContext ResponseWrapper.Success(response.value)
                else -> return@withContext response
            }
        }
    }

}