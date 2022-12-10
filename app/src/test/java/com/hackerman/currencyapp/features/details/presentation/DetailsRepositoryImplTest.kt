package com.hackerman.currencyapp.features.details.presentation
import com.hackerman.currencyapp.features.details.data.network.api.DetailsApi
import com.hackerman.currencyapp.features.details.data.network.model.GetHistoricDataResponse
import com.hackerman.currencyapp.features.details.data.network.model.GetPopularCurrenciesResponse
import com.hackerman.currencyapp.features.details.data.repository.DetailsRepositoryImpl
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
class DetailsRepositoryImplTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var mockApi: DetailsApi
    private lateinit var mockHistoricDataResponse: GetHistoricDataResponse


    @Before
    fun setup() {
        mockApi = Mockito.mock(DetailsApi::class.java)
        mockHistoricDataResponse = Mockito.mock(GetHistoricDataResponse::class.java)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get historic data is successful`() = runBlocking {
        val startDate = ""
        val endDate = ""
        val from = ""
        val to = ""
        Mockito.`when`(mockApi.getHistoricalData(startDate, endDate, from, to)).thenReturn(mockHistoricDataResponse)
        val repositoryImpl = DetailsRepositoryImpl(mockApi)
        val response = repositoryImpl.getHistoricData(startDate, endDate, from, to)
        assert(response==mockHistoricDataResponse)
    }


    @Test
    fun `get popular currencies is successful`() = runBlocking {
        val base = "AAA"
        val symbols =""
        val mockPopularCurrenciesResponse = Mockito.mock(GetPopularCurrenciesResponse::class.java)
        Mockito.`when`(mockApi.getPopularCurrencies(base, symbols)).thenReturn(mockPopularCurrenciesResponse)
        val repositoryImpl = DetailsRepositoryImpl(mockApi)
        val response = repositoryImpl.getPopularCurrencies(base)
        assert(response==mockPopularCurrenciesResponse)
    }

}