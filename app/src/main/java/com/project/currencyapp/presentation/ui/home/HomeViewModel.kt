package com.project.currencyapp.presentation.ui.home

import android.text.Editable
import android.view.View
import androidx.lifecycle.*
import com.project.currencyapp.R
import com.project.currencyapp.domain.models.common.ResponseWrapper
import com.project.currencyapp.domain.models.convert.CurrencyConvertResponse
import com.project.currencyapp.domain.repository.CurrencyRepository
import com.project.currencyapp.presentation.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: CurrencyRepository) : BaseViewModel() {


    val currencyListLiveData: LiveData<Set<String>>
        get() = _currencyListLiveData
    private val _currencyListLiveData: MutableLiveData<Set<String>> = MutableLiveData(emptySet())


    val convertResultLiveData: LiveData<CurrencyConvertResponse?>
        get() = _convertResultLiveData
    private val _convertResultLiveData: MutableLiveData<CurrencyConvertResponse?> =
        MutableLiveData()

    val fromCurrency: MutableLiveData<String>
        get() = _fromCurrency

    private val _fromCurrency: MutableLiveData<String> =
        MutableLiveData()

    fun updateFromCurrency(s: Editable) {
        _fromCurrency.value = s.toString()
        checkValues()

    }

    val toCurrency: MutableLiveData<String>
        get() = _toCurrency
    private var _toCurrency: MutableLiveData<String> = MutableLiveData()

    val amount: MutableLiveData<String>
        get() = _amount
    private var _amount: MutableLiveData<String> = MutableLiveData("1")

    fun updateToCurrency(s: Editable) {
        _toCurrency.value = s.toString()
        checkValues()
    }

    fun updateAmount(s: Editable) {
        _amount.value = s.toString()
        checkValues()
    }

    private fun checkValues() {
        if (!_fromCurrency.value.isNullOrEmpty()
            && !_toCurrency.value.isNullOrEmpty()
            && !_amount.value.isNullOrEmpty()
        ) {
            convertCurrency()

        }
    }

    fun swapValues() {

        if (!_fromCurrency.value.isNullOrEmpty()
            && !_toCurrency.value.isNullOrEmpty()
        ) {

            // swap values of two dropdown selected items
            val temp = _fromCurrency.value
            _fromCurrency.value = _toCurrency.value
            _toCurrency.value = temp!!

            if (!_amount.value.isNullOrEmpty()) convertCurrency()
        }

    }

    val navigateDestination: LiveData<Int?>
        get() = _navigateDestination
    private val _navigateDestination = MutableLiveData<Int?>(null)

    fun navigateToDetails(view: View) {

        if (!_fromCurrency.value.isNullOrEmpty()
            && !_toCurrency.value.isNullOrEmpty()
        ) {
            _navigateDestination.value = R.id.action_navigation_home_to_detailsFragment
            _navigateDestination.value = null
            _showMessage.value = null
        } else {
            _showMessage.value = "Please Select Currencies"
        }


    }

    init {
        // get all currency symbols when viewModel get initialized
        getCurrencySymbols()
    }


    private fun getCurrencySymbols() {
        viewModelScope.launch {

            _isLoading.value = true
            val response = repository.getCurrencySymbols()
            when (response) {
                is ResponseWrapper.Success -> _currencyListLiveData.value =
                    response.value.symbols.keys
                else -> onResponseError(response)
            }
            _isLoading.value = false
        }
    }

    fun convertCurrency() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.convertCurrency(
                _fromCurrency.value!!,
                _toCurrency.value!!,
                Integer.parseInt(_amount.value!!)
            )
            when (response) {
                is ResponseWrapper.Success -> _convertResultLiveData.value = response.value
                else -> onResponseError(response)
            }

            _isLoading.value = false
        }
    }

    class Factory constructor(private val repository: CurrencyRepository) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                HomeViewModel(this.repository) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}