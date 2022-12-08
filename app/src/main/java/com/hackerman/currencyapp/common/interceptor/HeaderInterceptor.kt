package com.hackerman.currencyapp.common.interceptor

import android.provider.SyncStateContract.Constants
import com.hackerman.currencyapp.common.constants.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(

) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            chain.request()
                .newBuilder()
                .addHeader("apikey", NetworkConstants.API_KEY)
                .build()
        )
    }
}
