package com.hackerman.currencyapp.common

import com.hackerman.currencyapp.common.resource.Resource
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetSymbolsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class ResponseFakeFlow <T>(
    private val emittedResource :Resource<T>
): Flow<Resource<T>> {
    override suspend fun collect(collector: FlowCollector<Resource<T>>) {
        collector.emit(emittedResource)
    }
}