package com.project.currencyapp.presentation.utils


import android.graphics.Color
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.project.currencyapp.domain.models.history.ConvertHistoryResponse
import com.project.currencyapp.presentation.ui.adapters.HistoryAdapter

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
    @BindingAdapter("actorsList")
    fun bindActorList(recyclerView: RecyclerView, historyResponse: ConvertHistoryResponse?) {
        historyResponse?.let { recyclerView.adapter = HistoryAdapter(it) }
    }
}