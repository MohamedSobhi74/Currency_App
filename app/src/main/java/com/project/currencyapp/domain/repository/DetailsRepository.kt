package com.project.currencyapp.domain.repository

import com.project.currencyapp.domain.models.CurrencySymbolsResponse
import com.project.currencyapp.domain.models.common.ResponseWrapper
import com.project.currencyapp.domain.models.convert.CurrencyConvertResponse
import com.project.currencyapp.domain.models.history.ConvertHistoryResponse
import retrofit2.http.Query

interface DetailsRepository {

    suspend fun getConvertHistory(
        base: String,
        symbols: String,
        startDate: String,
        endDate: String
    ): ResponseWrapper<ConvertHistoryResponse>
}