package com.hackerman.currencyapp.features.details.data.repository

import com.hackerman.currencyapp.features.details.data.network.api.DetailsApi
import com.hackerman.currencyapp.features.details.data.network.model.GetHistoricDataResponse
import com.hackerman.currencyapp.features.details.data.network.model.GetPopularCurrenciesResponse
import com.hackerman.currencyapp.features.details.domain.repository.DetailsRepository
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi
):DetailsRepository {
    override suspend fun getHistoricData(
        startDate: String,
        endDate: String,
        from: String,
        to: String
    ): GetHistoricDataResponse {
       return detailsApi.getHistoricalData(startDate, endDate, from, to)
    }

    override suspend fun getPopularCurrencies(base: String): GetPopularCurrenciesResponse {
        return detailsApi.getPopularCurrencies(base,"")
    }
}