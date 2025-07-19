package com.example.shopthoitrang.model

data class Product(
    val Id:Int?=null,
    val Name:String?=null,
    val Price: Double? = null,
    val Image: List<String> = emptyList<String>(),
    val Description: String? = null,
    val CategoryId: Int? = null,
    val Quantity: Int? = null,
)
