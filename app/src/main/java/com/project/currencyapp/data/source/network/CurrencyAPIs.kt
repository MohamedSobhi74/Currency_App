package com.project.currencyapp.data.source.network

import com.project.currencyapp.domain.models.CurrencySymbolsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET


val currencyAPIs: CurrencyAPIs by lazy {
    retrofit.create(CurrencyAPIs::class.java)
}
interface CurrencyAPIs {

    @GET("symbols")
     fun getCurrencySymbols() : Deferred<CurrencySymbolsResponse>
}