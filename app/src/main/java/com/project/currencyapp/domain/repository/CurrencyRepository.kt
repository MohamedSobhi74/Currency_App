package com.project.currencyapp.domain.repository

import com.project.currencyapp.domain.models.CurrencySymbolsResponse
import com.project.currencyapp.domain.models.common.ResponseWrapper

interface CurrencyRepository {

    suspend fun getCurrencySymbols() :ResponseWrapper<CurrencySymbolsResponse>
}