package com.example.apisampleapp.ui.model

import com.example.apisampleapp.data.model.response.Quotation

data class Item(
    val uid: String,
    val title: String,
    val subTitle: String,
    val price: Quotation? = null,
)
