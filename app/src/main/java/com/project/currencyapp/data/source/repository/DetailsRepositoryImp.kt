package com.project.currencyapp.data.source.repository

import com.project.currencyapp.data.source.network.CurrencyAPIs
import com.project.currencyapp.data.source.network.currencyAPIs
import com.project.currencyapp.data.source.network.safeApiCall
import com.project.currencyapp.domain.models.CurrencySymbolsResponse
import com.project.currencyapp.domain.models.common.ResponseWrapper
import com.project.currencyapp.domain.models.convert.CurrencyConvertResponse
import com.project.currencyapp.domain.models.history.ConvertHistoryResponse
import com.project.currencyapp.domain.repository.CurrencyRepository
import com.project.currencyapp.domain.repository.DetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Query

val detailsRepository: DetailsRepository by lazy {
    DetailsRepositoryImp(currencyAPIs = currencyAPIs)
}

class DetailsRepositoryImp(
    val currencyAPIs: CurrencyAPIs,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DetailsRepository {

    override suspend fun getConvertHistory(
        base: String,
        symbols: String,
        startDate: String,
        endDate: String
    ): ResponseWrapper<ConvertHistoryResponse> {

        return withContext(dispatcher) {
            val response = safeApiCall {
                currencyAPIs.getHistory(base,symbols,startDate, endDate)
            }
            when (response) {
                is ResponseWrapper.Success -> return@withContext ResponseWrapper.Success(response.value)
                else -> return@withContext response
            }
        }
    }

}