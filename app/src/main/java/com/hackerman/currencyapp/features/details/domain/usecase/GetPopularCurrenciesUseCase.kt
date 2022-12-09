package com.hackerman.currencyapp.features.details.domain.usecase

import com.hackerman.currencyapp.common.errorhelper.ErrorHandler
import com.hackerman.currencyapp.common.resource.Resource
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetConversionResponse
import com.hackerman.currencyapp.features.details.data.network.model.GetPopularCurrenciesResponse
import com.hackerman.currencyapp.features.details.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPopularCurrenciesUseCase @Inject constructor(
    private val repository: DetailsRepository,
    private val errorHandler: ErrorHandler,
) {

    operator fun invoke(base: String): Flow<Resource<GetPopularCurrenciesResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getPopularCurrencies(base)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = errorHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}