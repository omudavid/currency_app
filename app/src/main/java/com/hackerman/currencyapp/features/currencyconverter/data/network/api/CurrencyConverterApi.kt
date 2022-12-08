package com.hackerman.currencyapp.features.currencyconverter.data.network.api

import com.hackerman.currencyapp.common.constants.NetworkConstants.API_KEY
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetConversionResponse
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetSymbolsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyConverterApi {

    @GET("symbols")
    suspend fun getCurrencySymbols(): GetSymbolsResponse

    @GET("convert")
    suspend fun convertCurrency(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: String,
    ): GetConversionResponse


}