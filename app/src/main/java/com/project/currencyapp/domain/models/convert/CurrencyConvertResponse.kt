package com.project.currencyapp.domain.models.convert

data class CurrencyConvertResponse(
    val date: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)

data class Info(
    val rate: Double,
    val timestamp: Int
)

data class Query(
    val amount: Int,
    val from: String,
    val to: String
)