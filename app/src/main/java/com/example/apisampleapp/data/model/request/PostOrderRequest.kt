package com.example.apisampleapp.data.model.request

import com.example.apisampleapp.data.model.response.Quotation

data class PostOrderRequest(
    val accountId: String,
    val instrumentId: String,
    val quantity: Long,
    val price: Quotation,
    val direction: OrderDirection,
    val orderType: OrderType,
)
