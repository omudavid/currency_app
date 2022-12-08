package com.hackerman.currencyapp.features.currencyconverter.presentation

import app.cash.turbine.test
import com.hackerman.currencyapp.common.ResponseFakeFlow
import com.hackerman.currencyapp.common.resource.Resource
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetConversionResponse
import com.hackerman.currencyapp.features.currencyconverter.data.network.model.GetSymbolsResponse
import com.hackerman.currencyapp.features.currencyconverter.domain.usecase.GetConvertCurrencyUseCase
import com.hackerman.currencyapp.features.currencyconverter.domain.usecase.GetSymbolsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrencyConverterViewModelTest {


    private val dispatcher = StandardTestDispatcher()
    private lateinit var mockGetConvertCurrencyUseCase: GetConvertCurrencyUseCase
    private lateinit var mockGetSymbolsUseCase: GetSymbolsUseCase
    private lateinit var mockSymbolsResponse: GetSymbolsResponse
    private lateinit var mockGetConversionResponse: GetConversionResponse


    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        mockGetConvertCurrencyUseCase = Mockito.mock(GetConvertCurrencyUseCase::class.java)
        mockGetSymbolsUseCase = Mockito.mock(GetSymbolsUseCase::class.java)
        mockSymbolsResponse = Mockito.mock(GetSymbolsResponse::class.java)
        mockGetConversionResponse = Mockito.mock(GetConversionResponse::class.java)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `view model goes into loading state`() = runTest {
        val flow = ResponseFakeFlow(Resource.Success(mockSymbolsResponse))
        Mockito.`when`(mockGetSymbolsUseCase.invoke()).thenReturn(flow)
        val viewModel =
            CurrencyConverterViewModel(mockGetSymbolsUseCase, mockGetConvertCurrencyUseCase)

        viewModel.getSymbolsResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
        }
    }


    @Test
    fun `view model gets symbols`() = runTest {
        val flow = ResponseFakeFlow(Resource.Success(mockSymbolsResponse))
        Mockito.`when`(mockGetSymbolsUseCase.invoke()).thenReturn(flow)
        val viewModel =
            CurrencyConverterViewModel(mockGetSymbolsUseCase, mockGetConvertCurrencyUseCase)

        viewModel.getSymbolsResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assert(secondEmission::class.java == Resource.Success::class.java)
        }
    }


    @Test
    fun `view model get converted data`() = runTest {
        val from = "AAA"
        val to = "AAA"
        val amount = "100"
        val flow = ResponseFakeFlow(Resource.Success(mockGetConversionResponse))
        Mockito.`when`(mockGetConvertCurrencyUseCase.invoke(from, to, amount)).thenReturn(flow)
        val viewModel =
            CurrencyConverterViewModel(mockGetSymbolsUseCase, mockGetConvertCurrencyUseCase)
        viewModel.getConvertedCurrency(from, to, amount)
        viewModel.getConvertedCurrencyResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assert(secondEmission::class.java == Resource.Success::class.java)
        }
    }


}