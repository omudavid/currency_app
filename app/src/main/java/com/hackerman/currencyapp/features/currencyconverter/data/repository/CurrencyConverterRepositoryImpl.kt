package com.hackerman.currencyapp.features.currencyconverter.data.repository

import com.hackerman.currencyapp.features.currencyconverter.data.network.api.CurrencyConverterApi
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetConversionResponse
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetSymbolsResponse
import com.hackerman.currencyapp.features.currencyconverter.domain.repository.CurrencyConverterRepository
import javax.inject.Inject

class CurrencyConverterRepositoryImpl  @Inject constructor(
    private val currencyConverterApi: CurrencyConverterApi
): CurrencyConverterRepository {
    override suspend fun getSymbols(): GetSymbolsResponse {
        return  currencyConverterApi.getCurrencySymbols()
    }

    override suspend fun convertCurrency(from:String,to:String,amount:String): GetConversionResponse {
        return  currencyConverterApi.convertCurrency(from, to, amount)
    }
}