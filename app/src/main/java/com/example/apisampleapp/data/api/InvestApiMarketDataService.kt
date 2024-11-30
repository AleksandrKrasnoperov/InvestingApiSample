package com.example.apisampleapp.data.api

import com.example.apisampleapp.data.model.request.GetLastPricesRequest
import com.example.apisampleapp.data.model.response.GetLastPricesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface InvestApiMarketDataService {

    private companion object {
        const val MARKET_DATA_SERVICE = "tinkoff.public.invest.api.contract.v1.MarketDataService"
    }

    @POST("$MARKET_DATA_SERVICE/GetLastPrices")
    suspend fun getLastPrices(
        @Body request: GetLastPricesRequest,
        @Header("Authorization") authHeader: String
    ): Response<GetLastPricesResponse>
}