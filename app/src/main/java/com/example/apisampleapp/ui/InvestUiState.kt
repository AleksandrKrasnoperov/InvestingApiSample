package com.example.apisampleapp.ui

import com.example.apisampleapp.ui.model.Item

data class InvestUiState(
    val query: String = "Apple",
    val items: List<Item> = emptyList(),
    val loading: Boolean? = false,
    val error: Throwable? = null,
    val orderResult: String? = null
)
