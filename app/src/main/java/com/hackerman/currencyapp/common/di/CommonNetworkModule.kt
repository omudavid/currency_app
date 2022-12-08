package com.hackerman.currencyapp.common.di

import android.content.Context
import com.hackerman.currencyapp.common.constants.NetworkConstants
import com.hackerman.currencyapp.common.errorhelper.ErrorHandler
import com.hackerman.currencyapp.common.interceptor.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonNetworkModule {
    /**
     * provide the logger
     * this logs out every response to and from the web service
     */
    @Provides
    @Singleton
    fun providesLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }



    /**
     * provide gsonconverter
     */
    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    /**
     * provide the OkHttp
     */
    @Provides
    @Singleton
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    }

    /**
     * it provides retrofit http client
     * for available endpoints to use
     */
    @Provides
    @Singleton
    fun providesRetrofitHttpClientForEndpointsToUse(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }



    /**
     * provide error handler
     */
    @Provides
    @Singleton
    fun provideErrorHandle(@ApplicationContext context: Context): ErrorHandler {
        return ErrorHandler(context)
    }

    @Provides
    @Singleton
    fun providesMobileHeaderInterceptor(): HeaderInterceptor {
        return HeaderInterceptor()
    }
}