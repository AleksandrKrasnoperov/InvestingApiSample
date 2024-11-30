package com.example.apisampleapp.data.model.response

data class PostOrderResponse(
    val orderId: String,
    val executionReportStatus: OrderExecutionReportStatus,
)
