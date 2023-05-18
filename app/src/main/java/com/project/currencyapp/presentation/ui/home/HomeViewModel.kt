package com.project.currencyapp.presentation.ui.home

import androidx.lifecycle.*
import com.project.currencyapp.domain.models.CurrencySymbolsResponse
import com.project.currencyapp.domain.models.common.ResponseWrapper
import com.project.currencyapp.domain.repository.CurrencyRepository
import kotlinx.coroutines.launch

class HomeViewModel(val repository: CurrencyRepository) : ViewModel() {

    val currencySymbolsResponseLiveData: LiveData<ResponseWrapper<CurrencySymbolsResponse>>
        get() = _currencySymbolsResponseLiveData
    private val _currencySymbolsResponseLiveData: MutableLiveData<ResponseWrapper<CurrencySymbolsResponse>> = MutableLiveData()


    fun getCurrencySymbols() {
        viewModelScope.launch {
            _currencySymbolsResponseLiveData.postValue( repository.getCurrencySymbols())
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