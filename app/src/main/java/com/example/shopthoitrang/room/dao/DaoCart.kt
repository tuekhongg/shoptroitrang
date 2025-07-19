package com.example.shopthoitrang.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shopthoitrang.room.entity.EntityCart

@Dao
interface DaoCart {
    @Query("select * from cart")
    suspend fun getAll(): List<EntityCart>

    @Insert
    suspend fun insertCart(entityCart: EntityCart)

    @Update
    suspend fun updateCart(entityCart: EntityCart)

    @Delete
    suspend fun deleteCart(entityCart: EntityCart)

    @Query("select * from cart where IdProduct = :id and Size = :size")
    suspend fun getCartByIdAndSize(id: Int, size: String): EntityCart?
}