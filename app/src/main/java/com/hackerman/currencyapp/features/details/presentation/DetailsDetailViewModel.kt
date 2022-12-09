package com.hackerman.currencyapp.features.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackerman.currencyapp.common.resource.Resource
import com.hackerman.currencyapp.features.details.data.network.model.GetHistoricDataResponse
import com.hackerman.currencyapp.features.details.data.network.model.GetPopularCurrenciesResponse
import com.hackerman.currencyapp.features.details.domain.usecase.GetHistoricDataUseCase
import com.hackerman.currencyapp.features.details.domain.usecase.GetPopularCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetailsDetailViewModel @Inject constructor(
    private val getPopularCurrenciesUseCase: GetPopularCurrenciesUseCase,
    private val getHistoricDataUseCase: GetHistoricDataUseCase
) : ViewModel() {

    private val _getHistoricData =
        MutableStateFlow<Resource<GetHistoricDataResponse>>(Resource.Loading())
    val getHistoricData: StateFlow<Resource<GetHistoricDataResponse>> get() = _getHistoricData

    private val _getPopularCurrencies =
        MutableStateFlow<Resource<GetPopularCurrenciesResponse>>(Resource.Loading())
    val getPopularCurrenciesResponse: StateFlow<Resource<GetPopularCurrenciesResponse>> get() = _getPopularCurrencies


    fun getHistoricData(fromCurrency: String, toCurrency: String) {
        viewModelScope.launch {
            getHistoricDataUseCase(
                getThreeDaysAgoDate(),
                getCurrentDate(),
                fromCurrency,
                toCurrency
            ).collect {
                _getHistoricData.value = it
            }
        }
    }

    fun getPopularCurrencies(fromCurrency: String){
        viewModelScope.launch {
            getPopularCurrenciesUseCase(fromCurrency).collect{
                _getPopularCurrencies.value = it
            }}
        }

    private fun getCurrentDate(): String {
        val calender = Calendar.getInstance()
        return formatDate(calender)
    }

    private fun formatDate(cal: Calendar): String {
        return "${cal[Calendar.YEAR]}-${cal[Calendar.MONTH] + 1}-${if (cal[Calendar.DATE] < 10) "0" + cal[Calendar.DATE] else cal[Calendar.DATE]}"

    }

    private fun getThreeDaysAgoDate(): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val calender = Calendar.getInstance()
        simpleDateFormat.format(calender.time)
        calender.add(Calendar.DATE, -2)
        return formatDate(calender)
    }

}