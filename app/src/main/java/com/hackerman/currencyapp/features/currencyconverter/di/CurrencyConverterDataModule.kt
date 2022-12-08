package com.hackerman.currencyapp.features.currencyconverter.di

import com.hackerman.currencyapp.features.currencyconverter.data.network.api.CurrencyConverterApi
import com.hackerman.currencyapp.features.currencyconverter.data.repository.CurrencyConverterRepositoryImpl
import com.hackerman.currencyapp.features.currencyconverter.domain.repository.CurrencyConverterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyConverterDataModule {

    @Provides
    @Singleton
    fun providesCurrencyConverterApi(retrofit: Retrofit): CurrencyConverterApi {
        return retrofit.create(CurrencyConverterApi::class.java)
    }

    @Provides
    @Singleton
    fun providesCurrencyConverterRepository(currencyConverterApi: CurrencyConverterApi): CurrencyConverterRepository {
        return CurrencyConverterRepositoryImpl(currencyConverterApi)
    }
}