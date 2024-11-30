package com.example.apisampleapp.data.api

import com.example.apisampleapp.data.model.request.PostOrderRequest
import com.example.apisampleapp.data.model.response.PostOrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface InvestApiOrdersService {

    private companion object {
        const val ORDERS_SERVICE = "tinkoff.public.invest.api.contract.v1.OrdersService"
    }

    @POST("$ORDERS_SERVICE/PostOrder")
    suspend fun postOrder(
        @Body request: PostOrderRequest,
        @Header("Authorization") authHeader: String
    ): Response<PostOrderResponse>
}