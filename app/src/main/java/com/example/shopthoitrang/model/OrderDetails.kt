package com.example.shopthoitrang.model

data class OrderDetails(
    val Id :Int ? =null,
    val OrderId: Int? = null,
    val ProductId: Int? = null,
    val Price: Double?=null,
    val Quantity: Int? = null,
    val Size: String? =null
)