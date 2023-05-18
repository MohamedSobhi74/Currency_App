package com.project.currencyapp.domain.models

data class CurrencySymbolsResponse(
    val success: Boolean,
    val symbols: Map<String,String>
)

