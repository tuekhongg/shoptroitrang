package com.example.shopthoitrang.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
class EntityCart(
    @PrimaryKey(autoGenerate = true)
    val Id:Int = 0,
    val IdProduct:Int,
    val Name:String,
    val Price:Double,
    var Quantity:Int,
    var Size:String,
    var Image :String
)
