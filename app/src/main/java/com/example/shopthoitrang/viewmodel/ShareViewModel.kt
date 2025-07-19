package com.example.shopthoitrang.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shopthoitrang.model.Order
import com.example.shopthoitrang.model.Product
import com.example.shopthoitrang.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShareViewModel : ViewModel() {
    private var _product = MutableStateFlow<Product?>(null)
    val product = _product.asStateFlow()
    private var _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()
    private var _order = MutableStateFlow<Order?>(null)
    val order = _order.asStateFlow()
    fun setProduct(product: Product) {
        _product.value = product
    }
    fun setUser(user: User?) {
        _user.value = user
    }
    fun setOrder(order: Order) {
        _order.value = order
    }
}