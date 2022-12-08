package com.hackerman.currencyapp.features.currencyconverter.data

import com.hackerman.currencyapp.features.currencyconverter.data.network.api.CurrencyConverterApi
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetConversionResponse
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetSymbolsResponse
import com.hackerman.currencyapp.features.currencyconverter.data.repository.CurrencyConverterRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrencyConverterRepositoryImplTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var mockApi: CurrencyConverterApi
    private lateinit var mockSymbolsResponse: GetSymbolsResponse


    @Before
    fun setup() {
        mockApi = Mockito.mock(CurrencyConverterApi::class.java)
        mockSymbolsResponse = Mockito.mock(GetSymbolsResponse::class.java)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get symbols is successful`() = runBlocking {
        Mockito.`when`(mockApi.getCurrencySymbols()).thenReturn(mockSymbolsResponse)
        val repositoryImpl = CurrencyConverterRepositoryImpl(mockApi)
        val response = repositoryImpl.getSymbols()
        assert(response==mockSymbolsResponse)
    }


    @Test
    fun `get converted currency is successful`() = runBlocking {
        val from = "AAA"
        val to ="AAA"
        val amount = "100"
        val mockConversionResponse = Mockito.mock(GetConversionResponse::class.java)
        Mockito.`when`(mockApi.convertCurrency(from,to,amount)).thenReturn(mockConversionResponse)
        val repositoryImpl = CurrencyConverterRepositoryImpl(mockApi)
        val response = repositoryImpl.convertCurrency(from,to, amount)
        assert(response==mockConversionResponse)
    }


}