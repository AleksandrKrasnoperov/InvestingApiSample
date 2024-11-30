package com.example.apisampleapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(INVEST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiInstrumentService: InvestApiInstrumentService =
        getRetrofit().create(InvestApiInstrumentService::class.java)

    val apiMarketDataService: InvestApiMarketDataService =
        getRetrofit().create(InvestApiMarketDataService::class.java)

    val apiUsersService: InvestApiUsersService =
        getRetrofit().create(InvestApiUsersService::class.java)

    val apiOrdersService: InvestApiOrdersService =
        getRetrofit().create(InvestApiOrdersService::class.java)
}