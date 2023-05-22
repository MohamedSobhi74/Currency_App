package com.project.currencyapp.domain.repository

import com.project.currencyapp.domain.models.common.ResponseWrapper
import com.project.currencyapp.domain.models.history.HistoryRatesResponse
import com.project.currencyapp.domain.models.latest.LatestRatesResponse

interface DetailsRepository {

    suspend fun getConvertHistory(
        base: String,
        symbols: String,
        startDate: String,
        endDate: String
    ): ResponseWrapper<HistoryRatesResponse>

    suspend fun getLatestRates(
        base: String
    ): ResponseWrapper<LatestRatesResponse>
}