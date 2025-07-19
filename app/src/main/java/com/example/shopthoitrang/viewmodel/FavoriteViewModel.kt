package com.example.shopthoitrang.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopthoitrang.room.database.DatabaseShop
import com.example.shopthoitrang.room.entity.EntityCart
import com.example.shopthoitrang.room.entity.EntityFavoriteProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(context : Context) : ViewModel() {
    private val daoFavoriteProduct = DatabaseShop.getInstance(context).daoFavoriteProduct()
    private val daoCart = DatabaseShop.getInstance(context).daoCart()
    private var _listFavoriteProduct = MutableStateFlow<List<EntityFavoriteProduct>>(emptyList())
    val listFavoriteProduct = _listFavoriteProduct.asStateFlow()
    private var _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()
    fun getListFavoriteProduct() {
        _isLoading.value = true
        viewModelScope.launch {
            _listFavoriteProduct.value = daoFavoriteProduct.getAll()
            _isLoading.value = false
        }
    }
    fun insertCart(entityCart: EntityCart){
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
}