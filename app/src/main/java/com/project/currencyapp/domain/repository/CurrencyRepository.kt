package com.project.currencyapp.domain.repository

import com.project.currencyapp.domain.models.CurrencySymbolsResponse
import com.project.currencyapp.domain.models.common.ResponseWrapper
import com.project.currencyapp.domain.models.convert.CurrencyConvertResponse

interface CurrencyRepository {

    suspend fun getCurrencySymbols() :ResponseWrapper<CurrencySymbolsResponse>
    suspend fun convertCurrency(from:String,to:String,amount:Int) :ResponseWrapper<CurrencyConvertResponse>
}