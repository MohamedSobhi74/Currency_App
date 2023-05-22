package com.project.currencyapp.presentation.utils


import android.graphics.Color
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.project.currencyapp.domain.models.history.HistoryRatesResponse
import com.project.currencyapp.domain.models.latest.LatestRatesResponse
import com.project.currencyapp.presentation.ui.adapters.HistoryAdapter
import com.project.currencyapp.presentation.ui.adapters.LatestRatesAdapter


object CustomBinding {
    @JvmStatic
    @BindingAdapter("dropList")
    fun setDropDownList(view: TextInputLayout?, values: Set<String>?) {

        val adapter = ArrayAdapter(
            view!!.context,
            android.R.layout.simple_dropdown_item_1line,
            values!!.toTypedArray()
        )
        (view.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    @JvmStatic
    @BindingAdapter("dropListSelection")
    fun setDropDownListSelection(view: TextInputLayout?, value: String?) {
        if (!value.isNullOrBlank()
            && !(view!!.editText as? AutoCompleteTextView)?.text!!.toString().equals(value)
        ) {
            (view!!.editText as? AutoCompleteTextView)?.setText(value, false)

        }
    }

    @JvmStatic
    @BindingAdapter("showSnackBar")
    fun showSnackBar(view: View, msg: String?) {
        if (!msg.isNullOrBlank()) {
            Snackbar.make(
                view,
                msg,
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(Color.RED).show()
        }
    }

    @JvmStatic
    @BindingAdapter("historyRatesList")
    fun bindHistoryList(recyclerView: RecyclerView, historyResponse: HistoryRatesResponse?) {
        historyResponse?.let { recyclerView.adapter = HistoryAdapter(it) }
    }

    @JvmStatic
    @BindingAdapter("latestRatesList")
    fun bindLatestList(recyclerView: RecyclerView, latestRatesResponse: LatestRatesResponse?) {
        latestRatesResponse?.let { recyclerView.adapter = LatestRatesAdapter(it) }
    }

    @JvmStatic
    @BindingAdapter("chartValues")
    fun bindChart(chart: AnyChartView, historyResponse: HistoryRatesResponse?) {

        historyResponse?.let {
            val rates = historyResponse!!.rates
            val column = AnyChart.column()
            val dataEntryList: MutableList<DataEntry> = ArrayList()



            for (position in 0 until rates.keys.size) {

                val date = historyResponse.rates.keys.toList().get(position)
                val symbol =
                    historyResponse.rates.values.toList().get(position).keys.toList().get(0)
                val value =
                    historyResponse.rates.values.toList().get(position).values.toList().get(0)

                dataEntryList.add(ValueDataEntry(date, value))


            }
            column.data(dataEntryList)
            chart.setChart(column)
        }

    }
}