package com.example.apisampleapp.data.model.response

data class Quotation(
    // Целая часть суммы, может быть отрицательным числом
    val units: Long,
    // Дробная часть суммы, может быть отрицательным числом
    val nano: Int
)
