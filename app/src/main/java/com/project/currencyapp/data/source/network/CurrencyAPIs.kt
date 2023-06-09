package com.project.currencyapp.data.source.network

import com.project.currencyapp.domain.models.CurrencySymbolsResponse
import com.project.currencyapp.domain.models.convert.CurrencyConvertResponse
import com.project.currencyapp.domain.models.history.HistoryRatesResponse
import com.project.currencyapp.domain.models.latest.LatestRatesResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyAPIs {

    @GET("symbols")
    fun getCurrencySymbols(): Deferred<CurrencySymbolsResponse>

    @GET("convert")
    fun convertCurreny(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Int
    ): Deferred<CurrencyConvertResponse>

    @GET("timeseries")
    fun getHistory(
        @Query("base") base: String,
        @Query("symbols") symbols: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Deferred<HistoryRatesResponse>

    @GET("latest")
    fun getLatestRates(
        @Query("base") base: String): Deferred<LatestRatesResponse>
}