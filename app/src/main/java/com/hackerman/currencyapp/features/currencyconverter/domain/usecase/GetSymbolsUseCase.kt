package com.hackerman.currencyapp.features.currencyconverter.domain.usecase

import com.hackerman.currencyapp.common.errorhelper.ErrorHandler
import com.hackerman.currencyapp.common.resource.Resource
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetSymbolsResponse
import com.hackerman.currencyapp.features.currencyconverter.domain.repository.CurrencyConverterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSymbolsUseCase @Inject constructor(
    private val repository: CurrencyConverterRepository,
    private val errorHandler: ErrorHandler,
){

    operator fun invoke(): Flow<Resource<GetSymbolsResponse>> = flow {
        try {
          emit(Resource.Loading())
            println("I was completed zz=z")
            val response = repository.getSymbols()
            println("I was completed zzz $response")
          emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = errorHandler.parse(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server check your internet connection"))
        }
    }
}