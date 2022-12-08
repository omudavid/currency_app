package com.hackerman.currencyapp.features.currencyconverter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackerman.currencyapp.common.resource.Resource
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetConversionResponse
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetSymbolsResponse
import com.hackerman.currencyapp.features.currencyconverter.domain.usecase.GetConvertCurrencyUseCase
import com.hackerman.currencyapp.features.currencyconverter.domain.usecase.GetSymbolsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val getSymbolsUseCase: GetSymbolsUseCase,
    private val getConvertCurrencyUseCase: GetConvertCurrencyUseCase
) : ViewModel() {

    private val _getSymbolsResponse =
        MutableStateFlow<Resource<GetSymbolsResponse>>(Resource.Loading())
    val getSymbolsResponse: StateFlow<Resource<GetSymbolsResponse>> get() = _getSymbolsResponse
    private val _getConvertedCurrencyResponse =
        MutableStateFlow<Resource<GetConversionResponse>>(Resource.Loading())
    val getConvertedCurrencyResponse: StateFlow<Resource<GetConversionResponse>> get() = _getConvertedCurrencyResponse


    init {
        getCurrencySymbols()
    }

    private fun getCurrencySymbols() {

        viewModelScope.launch {
            getSymbolsUseCase().collect {
                _getSymbolsResponse.value = it
            }
        }
    }

    fun getConvertedCurrency(from: String, to: String, amount: String) {
        viewModelScope.launch {
            getConvertCurrencyUseCase(from, to, amount).collect {
                _getConvertedCurrencyResponse.value = it
            }
        }
    }

}