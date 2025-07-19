package com.example.shopthoitrang.model

data class Order(
    val Id: Int? = null,
    val UserId: String? = null,
    val Name:String?=null,
    val Phone: String? = null,
    val Location: String? = null,
    val OrderTime:String? =null,
    val Status: Boolean?=null
)
