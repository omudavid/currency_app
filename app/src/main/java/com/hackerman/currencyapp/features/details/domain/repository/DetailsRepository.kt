package com.hackerman.currencyapp.features.details.domain.repository

import com.hackerman.currencyapp.features.details.data.network.model.GetHistoricDataResponse
import com.hackerman.currencyapp.features.details.data.network.model.GetPopularCurrenciesResponse

interface DetailsRepository {

    suspend fun getHistoricData(startDate:String, endDate:String, from:String, to:String):GetHistoricDataResponse

    suspend fun getPopularCurrencies(base:String):GetPopularCurrenciesResponse
}