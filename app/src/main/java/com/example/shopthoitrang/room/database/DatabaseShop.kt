package com.example.shopthoitrang.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shopthoitrang.room.dao.DaoCart
import com.example.shopthoitrang.room.dao.DaoFavoriteProduct
import com.example.shopthoitrang.room.entity.EntityCart
import com.example.shopthoitrang.room.entity.EntityFavoriteProduct

@Database(entities = [EntityCart::class, EntityFavoriteProduct::class], version = 1, exportSchema = false)
abstract class DatabaseShop : RoomDatabase() {
    abstract fun daoCart() : DaoCart
    abstract fun daoFavoriteProduct() : DaoFavoriteProduct
    companion object{
        private var instance : DatabaseShop ?=null
        fun getInstance(context : Context): DatabaseShop {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context,
                    DatabaseShop::class.java,
                    "shop_database"
                )
                    .build()
                instance = newInstance
                newInstance
            }
        }
    }
}