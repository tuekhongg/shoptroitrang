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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(context : Context) : ViewModel() {
    private val fireBase = FirebaseFirestore.getInstance()
    private val collectionCategory = fireBase.collection("Category")
    private val collectionProduct = fireBase.collection("Product")
    private val daoCart = DatabaseShop.getInstance(context).daoCart()
    private var _selectedCategory = mutableIntStateOf(0)
    val selectedCategory = _selectedCategory
    private var _listCategory = MutableStateFlow<List<Category>>(emptyList())
    private var _listProduct = MutableStateFlow<List<Product>>(emptyList())
    val listProduct = _listProduct.asStateFlow()
    val listCategory = _listCategory.asStateFlow()
    private var _searchQuery = MutableStateFlow<String>("")
    val searchQuery = _searchQuery.asStateFlow()
    var _isSearchLoading = MutableStateFlow<Boolean>(false)
    val isSearchLoading = _isSearchLoading.asStateFlow()
    var _noResult = MutableStateFlow<Boolean>(false)
    val noResult = _noResult.asStateFlow()
    init {
        loadCategory()
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setIsSearchLoading(isLoading: Boolean) {
        _isSearchLoading.value = isLoading
    }

    fun setSelectedCategory(selected: Int) {
        _selectedCategory.intValue = selected
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

    fun searchProduct() {
        try {
            collectionProduct.addSnapshotListener { snapshot, error ->
                _isSearchLoading.value = true
                if (error != null)
                    return@addSnapshotListener
                if (snapshot != null) {
                    var list = mutableListOf<Product>()
                    snapshot.forEach {
                        val product = it.toObject(Product::class.java)
                        if (_selectedCategory.intValue != 0) {
                            if (product.CategoryId != null && product.CategoryId - 1 == _selectedCategory.intValue)
                                if (product.Name != null && product.Name.contains(
                                        _searchQuery.value,
                                        ignoreCase = true
                                    )
                                )
                                    list.add(product)
                        } else
                            if (product.Name != null && product.Name.contains(
                                    _searchQuery.value,
                                    ignoreCase = true
                                )
                            )
                                list.add(product)
                    }
                    _listProduct.value = list
                    if(_listProduct.value.isEmpty()) _noResult.value= true
                    else _noResult.value = false
                }
                _isSearchLoading.value = false
            }
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
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