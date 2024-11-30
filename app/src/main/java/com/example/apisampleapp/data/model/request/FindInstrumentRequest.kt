package com.example.apisampleapp.data.model.request

import com.google.gson.annotations.SerializedName

data class FindInstrumentRequest(
    val query: String?,
    val instrumentType: InstrumentType?,
    @SerializedName("api_trade_available_flag")
    val apiTradeAvailableFlag: Boolean?
)
