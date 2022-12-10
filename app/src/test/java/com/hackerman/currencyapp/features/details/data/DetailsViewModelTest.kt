package com.hackerman.currencyapp.features.details.data

import app.cash.turbine.test
import com.hackerman.currencyapp.common.ResponseFakeFlow
import com.hackerman.currencyapp.common.resource.Resource
import com.hackerman.currencyapp.features.currencyconverter.presentation.CurrencyConverterViewModel
import com.hackerman.currencyapp.features.details.data.network.model.GetHistoricDataResponse
import com.hackerman.currencyapp.features.details.data.network.model.GetPopularCurrenciesResponse
import com.hackerman.currencyapp.features.details.domain.usecase.GetHistoricDataUseCase
import com.hackerman.currencyapp.features.details.domain.usecase.GetPopularCurrenciesUseCase
import com.hackerman.currencyapp.features.details.presentation.DetailsViewModel
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
class DetailsViewModelTest {


    private val dispatcher = StandardTestDispatcher()
    private lateinit var mockHistoricDataUseCase : GetHistoricDataUseCase
    private lateinit var mockPopularCurrenciesUseCase: GetPopularCurrenciesUseCase
    private lateinit var mockHistoricDataResponse: GetHistoricDataResponse
    private lateinit var mockGetPopularCurrenciesResponse: GetPopularCurrenciesResponse


    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        mockHistoricDataUseCase = Mockito.mock(GetHistoricDataUseCase::class.java)
        mockPopularCurrenciesUseCase = Mockito.mock(GetPopularCurrenciesUseCase::class.java)
        mockHistoricDataResponse = Mockito.mock(GetHistoricDataResponse::class.java)
        mockGetPopularCurrenciesResponse = Mockito.mock(GetPopularCurrenciesResponse::class.java)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `view model goes into loading state`() = runTest {
        val startDate = ""
        val endDate = ""
        val from = ""
        val to = ""
        val flow = ResponseFakeFlow(Resource.Success(mockHistoricDataResponse))
        Mockito.`when`(mockHistoricDataUseCase.invoke(startDate, endDate, from, to)).thenReturn(flow)
        val viewModel =
            DetailsViewModel(mockPopularCurrenciesUseCase, mockHistoricDataUseCase)

        viewModel.getHistoricData.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
        }
    }


    @Test
    fun `view model gets historic data`() = runTest {
        val startDate = ""
        val endDate = ""
        val from = ""
        val to = ""
        val flow = ResponseFakeFlow(Resource.Success(mockHistoricDataResponse))
        Mockito.`when`(mockHistoricDataUseCase.invoke(startDate, endDate, from, to)).thenReturn(flow)
        val viewModel =
            DetailsViewModel(mockPopularCurrenciesUseCase, mockHistoricDataUseCase)

        viewModel.getHistoricData(from,to)

        viewModel.getHistoricData.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assert(secondEmission::class.java == Resource.Success::class.java)
        }
    }


    @Test
    fun `view model get popular currencies`() = runTest {
        val base = "AAA"
        val symbols =""
        val flow = ResponseFakeFlow(Resource.Success(mockGetPopularCurrenciesResponse))
        Mockito.`when`(mockPopularCurrenciesUseCase.invoke(base)).thenReturn(flow)
        val viewModel =
            DetailsViewModel(mockPopularCurrenciesUseCase, mockHistoricDataUseCase)
        viewModel.getPopularCurrencies(base)
        viewModel.getPopularCurrenciesResponse.test {
            val emission = awaitItem()
            assert(emission::class.java == Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assert(secondEmission::class.java == Resource.Success::class.java)
        }
    }
}