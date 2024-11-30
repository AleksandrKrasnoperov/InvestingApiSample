package com.example.apisampleapp.domain

import com.example.apisampleapp.data.api.HEADER
import com.example.apisampleapp.data.api.RetrofitBuilder
import com.example.apisampleapp.data.model.request.InstrumentType
import com.example.apisampleapp.data.model.request.FindInstrumentRequest
import com.example.apisampleapp.data.model.request.GetAccountsRequest
import com.example.apisampleapp.data.model.request.GetLastPricesRequest
import com.example.apisampleapp.data.model.request.OrderDirection
import com.example.apisampleapp.data.model.request.OrderType
import com.example.apisampleapp.data.model.request.PostOrderRequest
import com.example.apisampleapp.data.model.response.FindInstrumentResponse
import com.example.apisampleapp.data.model.response.GetAccountsResponse
import com.example.apisampleapp.data.model.response.GetLastPricesResponse
import com.example.apisampleapp.data.model.response.PostOrderResponse
import com.example.apisampleapp.data.model.response.Quotation
import retrofit2.Response

class InvestRepository {

    private val apiInstrumentService = RetrofitBuilder.apiInstrumentService
    private val apiMarketDataService = RetrofitBuilder.apiMarketDataService
    private val apiUsersService = RetrofitBuilder.apiUsersService
    private val apiOrdersService = RetrofitBuilder.apiOrdersService

    suspend fun findInstrument(query: String): Response<FindInstrumentResponse> {
        return apiInstrumentService.findInstrument(
            FindInstrumentRequest(query, InstrumentType.INSTRUMENT_TYPE_SHARE, true),
            HEADER
        )
    }

    suspend fun getLastPrices(instrumentId: List<String>): Response<GetLastPricesResponse> {
        return apiMarketDataService.getLastPrices(
            GetLastPricesRequest(instrumentId),
            HEADER
        )
    }

    suspend fun getAccounts(): Response<GetAccountsResponse> {
        return apiUsersService.getAccounts(
            GetAccountsRequest(),
            HEADER
        )
    }

    suspend fun postOrder(accountId: String, instrumentId: String, price: Quotation): Response<PostOrderResponse> {
        return apiOrdersService.postOrder(
            PostOrderRequest(
                accountId,
                instrumentId,
                1L,
                price,
                OrderDirection.ORDER_DIRECTION_BUY,
                OrderType.ORDER_TYPE_MARKET
            ),
            HEADER
        )
    }
}