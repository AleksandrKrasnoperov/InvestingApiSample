package com.example.apisampleapp.data.api

import com.example.apisampleapp.data.model.request.FindInstrumentRequest
import com.example.apisampleapp.data.model.response.FindInstrumentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface InvestApiInstrumentService {

    private companion object {
        const val INSTRUMENT_SERVICE = "tinkoff.public.invest.api.contract.v1.InstrumentsService"
    }

    @POST("$INSTRUMENT_SERVICE/FindInstrument")
    suspend fun findInstrument(
        @Body request: FindInstrumentRequest,
        @Header("Authorization") authHeader: String
    ): Response<FindInstrumentResponse>
}