package com.example.shopthoitrang.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopthoitrang.model.Category
import com.example.shopthoitrang.model.Product
import com.example.shopthoitrang.room.database.DatabaseShop
import com.example.shopthoitrang.room.entity.EntityCart
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(context: Context) : ViewModel() {
    private val fireBase = FirebaseFirestore.getInstance()
    private val collectionCategory = fireBase.collection("Category")
    private val collectionProduct = fireBase.collection("Product")
    private val daoCart = DatabaseShop.getInstance(context).daoCart()
    private var _selectedCategory = mutableIntStateOf(0)
    val selectedCategory = _selectedCategory
    private var _listCategory = MutableStateFlow<List<Category>>(emptyList())
    val listCategory = _listCategory.asStateFlow()
    private var _listProduct = MutableStateFlow<List<Product>>(emptyList())
    val listProduct = _listProduct.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        loadCategory()
        loadProduct()
    }

    fun loadCategory() {
        try {
            collectionCategory.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.d("error", error.message.toString())
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    var list = mutableListOf<Category>()
                    snapshot.forEach {
                        list.add(it.toObject(Category::class.java))
                    }
                    _listCategory.value = list
                }
            }
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
        }
    }

    fun loadProduct() {
        try {
            collectionProduct.orderBy("Id", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null)
                        return@addSnapshotListener
                    if (snapshot != null) {
                        var list = mutableListOf<Product>()
                        snapshot.forEach {
                            val product = it.toObject(Product::class.java)
                            if (_selectedCategory.intValue != 0) {
                                if (product.CategoryId != null && product.CategoryId - 1 == _selectedCategory.intValue)
                                    list.add(product)
                            } else
                                list.add(product)
                        }
                        _listProduct.value = list
                    }
                }
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
        }
    }

    fun setSelectedCategory(index: Int) {
        _selectedCategory.intValue = index
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
}