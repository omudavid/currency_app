package com.hackerman.currencyapp.features.details.data.network.api

import com.hackerman.currencyapp.features.details.data.network.model.GetHistoricDataResponse
import com.hackerman.currencyapp.features.details.data.network.model.GetPopularCurrenciesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailsApi {

    @GET("timeseries")
    suspend fun getHistoricalData(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("base") from: String,
        @Query("symbols") to: String,
    ): GetHistoricDataResponse


    @GET("latest")
    suspend fun getPopularCurrencies(
        @Query("base") base: String,
        @Query("symbols") symbols: String,
    ):GetPopularCurrenciesResponse
}