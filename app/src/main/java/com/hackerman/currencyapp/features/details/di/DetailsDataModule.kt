package com.hackerman.currencyapp.features.details.di


import com.hackerman.currencyapp.features.details.data.network.api.DetailsApi
import com.hackerman.currencyapp.features.details.data.repository.DetailsRepositoryImpl
import com.hackerman.currencyapp.features.details.domain.repository.DetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailsDataModule {


    @Provides
    @Singleton
    fun providesDetailsApi(retrofit: Retrofit): DetailsApi {
        return retrofit.create(DetailsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesDetailsRepository(detailsApi: DetailsApi): DetailsRepository {
        return DetailsRepositoryImpl(detailsApi)
    }
}