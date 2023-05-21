package com.project.currencyapp.presentation.ui.details

import androidx.lifecycle.*
import com.project.currencyapp.domain.models.common.ResponseWrapper
import com.project.currencyapp.domain.models.history.ConvertHistoryResponse
import com.project.currencyapp.domain.repository.DetailsRepository
import com.project.currencyapp.presentation.ui.BaseViewModel
import com.project.currencyapp.presentation.utils.DateConverter
import kotlinx.coroutines.launch
import java.util.*

class DetailsViewModel(val repository: DetailsRepository) : BaseViewModel() {


    val convertHistoryResponseLiveData: LiveData<ConvertHistoryResponse?>
        get() = _convertHistoryResponseMutableLiveData
    private val _convertHistoryResponseMutableLiveData: MutableLiveData<ConvertHistoryResponse?> =
        MutableLiveData()


    val base: MutableLiveData<String>
        get() = _base

    private val _base: MutableLiveData<String> =
        MutableLiveData()

    val symbols: MutableLiveData<String>
        get() = _symbols

    private val _symbols: MutableLiveData<String> =
        MutableLiveData()

    // get date two days ago
    private var startDate: String? = DateConverter.getCalculatedDate("yyyy-MM-dd", -2)
    private var endDate: String? = DateConverter.getTodayDate("yyyy-MM-dd")


     fun getConvertHistory() {

        if (base.value.isNullOrEmpty()
            ||symbols.value.isNullOrEmpty()
            ||startDate.isNullOrEmpty()
            ||endDate.isNullOrEmpty()
        ) {
            _showMessage.value = "Something went wrong"
            return
        }
        viewModelScope.launch {

            _isLoading.value = true
            val response = repository.getConvertHistory(base.value!!, symbols.value!!, startDate!!, endDate!!)
            when (response) {
                is ResponseWrapper.Success -> _convertHistoryResponseMutableLiveData.value =
                    response.value
                else -> onResponseError(response)
            }
            _isLoading.value = false
        }
    }

    class Factory constructor(private val repository: DetailsRepository) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                DetailsViewModel(this.repository) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }


}