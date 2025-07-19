package com.example.shopthoitrang.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopthoitrang.room.database.DatabaseShop
import com.example.shopthoitrang.room.entity.EntityCart
import com.example.shopthoitrang.room.entity.EntityFavoriteProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsProductViewModel(context: Context) : ViewModel() {
    private val daoFavoriteProduct = DatabaseShop.getInstance(context).daoFavoriteProduct()
    private var _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite = _isFavorite.asStateFlow()
    private var _selectedImage = MutableStateFlow<Int>(0)
    val selectedImage = _selectedImage.asStateFlow()
    private var _selectedSize = MutableStateFlow<Int>(0)
    val selectedSize = _selectedSize.asStateFlow()
    private val daoCart = DatabaseShop.getInstance(context).daoCart()

    fun setSelectedImage(indexImage: Int) {
        _selectedImage.value = indexImage
    }

    fun setSelectedSize(indexSize: Int) {
        _selectedSize.value = indexSize
    }

    fun insertCart(entityCart: EntityCart) {
        viewModelScope.launch {
            val cart = daoCart.getCartByIdAndSize(entityCart.IdProduct, entityCart.Size)
            if (cart == null) {
                daoCart.insertCart(entityCart)
            } else {
                cart.Quantity += entityCart.Quantity
                daoCart.updateCart(cart)
            }
        }
    }
    fun setIsFavorite(isFavorite: Boolean){
        _isFavorite.value = isFavorite
    }
    fun insertFavoriteProduct(entityFavoriteProduct: EntityFavoriteProduct) {
        viewModelScope.launch {
            daoFavoriteProduct.insertFavoriteProduct(entityFavoriteProduct)
            _isFavorite.value = true
        }
    }
    fun deleteFavoriteProduct(entityFavoriteProduct: EntityFavoriteProduct) {
        viewModelScope.launch {
            daoFavoriteProduct.deleteFavoriteProduct(entityFavoriteProduct)
            _isFavorite.value = false
        }
    }

    fun getFavoriteProductById(idProduct: Int) {
       try {
           viewModelScope.launch {
               if (daoFavoriteProduct.getFavoriteProductById(idProduct) == null)
                   _isFavorite.value = false
               else
                   _isFavorite.value = true

           }
       }
       catch (e: Exception){
           Log.d("favorite",e.message.toString())
       }
    }
}