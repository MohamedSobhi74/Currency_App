package com.project.currencyapp.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    fun getTodayDate(dateFormat: String?):String?{
        val calendar = Calendar.getInstance();
        val dateFormat = SimpleDateFormat(dateFormat);
        return dateFormat.format(calendar.time);
    }

    fun getCalculatedDate(dateFormat: String?, days: Int): String? {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(dateFormat)
        calendar.add(Calendar.DAY_OF_YEAR, days)
        return dateFormat.format(Date(calendar.timeInMillis))
    }
}