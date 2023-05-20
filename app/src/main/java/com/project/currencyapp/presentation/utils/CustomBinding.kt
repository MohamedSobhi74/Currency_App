package com.project.currencyapp.presentation.utils


import android.graphics.Color
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

object CustomBinding {
    @JvmStatic
    @BindingAdapter("dropList")
    fun setDropDownList(view: TextInputLayout?,values: Set<String>?) {

        val adapter = ArrayAdapter(
            view!!.context,
            android.R.layout.simple_dropdown_item_1line,
            values!!.toTypedArray()
        )
        (view.editText as? AutoCompleteTextView)?.setAdapter(adapter)
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

}