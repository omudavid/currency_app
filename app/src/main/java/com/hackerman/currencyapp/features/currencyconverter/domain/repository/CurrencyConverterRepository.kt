package com.hackerman.currencyapp.features.currencyconverter.domain.repository

import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetConversionResponse
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetSymbolsResponse

interface CurrencyConverterRepository {

   suspend fun getSymbols():GetSymbolsResponse

   suspend fun convertCurrency(from:String,to:String,amount:String): GetConversionResponse
}