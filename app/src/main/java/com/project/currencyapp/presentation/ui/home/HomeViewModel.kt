package com.project.currencyapp.presentation.ui.home

import androidx.lifecycle.*
import com.project.currencyapp.domain.models.common.ResponseWrapper
import com.project.currencyapp.domain.models.convert.CurrencyConvertResponse
import com.project.currencyapp.domain.repository.CurrencyRepository
import com.project.currencyapp.presentation.ui.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(val repository: CurrencyRepository) : BaseViewModel() {

 /*   val currencySymbolsResponseLiveData: LiveData<ResponseWrapper<CurrencySymbolsResponse>>
        get() = _currencySymbolsResponseLiveData
    private val _currencySymbolsResponseLiveData: MutableLiveData<ResponseWrapper<CurrencySymbolsResponse>> =
        MutableLiveData()
*/

    val currencyListLiveData: LiveData<Set<String>>
        get() = _currencyListLiveData
    private val _currencyListLiveData: MutableLiveData<Set<String>> = MutableLiveData(emptySet())


    val convertResultLiveData: LiveData<ResponseWrapper<CurrencyConvertResponse>>
        get() = _convertResultLiveData
    private val _convertResultLiveData: MutableLiveData<ResponseWrapper<CurrencyConvertResponse>> =
        MutableLiveData()


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

    fun convertCurrency(from: String, to: String, amount: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.convertCurrency(from, to, amount)
            when (response) {
                is ResponseWrapper.Success -> _convertResultLiveData.value = response
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