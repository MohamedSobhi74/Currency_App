package com.project.currencyapp.domain.models.latest

data class LatestRatesResponse(
    val base: String,
    val end_date: String,
    val rates: Map<String,Double>,
    val start_date: String,
    val success: Boolean,
    val timeseries: Boolean
)
