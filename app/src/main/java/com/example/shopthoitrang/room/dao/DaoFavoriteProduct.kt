package com.example.shopthoitrang.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.shopthoitrang.room.entity.EntityFavoriteProduct

@Dao
interface DaoFavoriteProduct {
    @Query("select * from favoriteproduct where Id = :id")
    suspend fun getFavoriteProductById(id: Int): EntityFavoriteProduct?

    @Query("select * from favoriteproduct")
    suspend fun getAll(): List<EntityFavoriteProduct>

    @Insert
    suspend fun insertFavoriteProduct(entityFavoriteProduct: EntityFavoriteProduct)

    @Delete
    suspend fun deleteFavoriteProduct(entityFavoriteProduct: EntityFavoriteProduct)
}