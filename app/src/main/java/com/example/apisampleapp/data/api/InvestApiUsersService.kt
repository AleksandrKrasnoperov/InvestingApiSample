package com.example.apisampleapp.data.api

import com.example.apisampleapp.data.model.request.GetAccountsRequest
import com.example.apisampleapp.data.model.request.PostOrderRequest
import com.example.apisampleapp.data.model.response.GetAccountsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface InvestApiUsersService {

    private companion object {
        const val USERS_SERVICE = "tinkoff.public.invest.api.contract.v1.UsersService"
    }

    @POST("$USERS_SERVICE/GetAccounts")
    suspend fun getAccounts(
        @Body request: GetAccountsRequest,
        @Header("Authorization") authHeader: String
    ): Response<GetAccountsResponse>
}