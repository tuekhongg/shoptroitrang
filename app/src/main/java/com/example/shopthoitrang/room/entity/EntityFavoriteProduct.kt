package com.example.shopthoitrang.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteproduct")
data class EntityFavoriteProduct(
    @PrimaryKey
    val Id: Int ,
    val Name: String,
    val Price: Double,
    val Image: String,
    val Description: String,
    val CategoryId: Int,
    val Quantity: Int,
)