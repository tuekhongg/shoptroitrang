package com.example.shopthoitrang.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.shopthoitrang.model.OrderDetails
import com.example.shopthoitrang.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderDetailsViewModel : ViewModel() {
    private val fireBase = FirebaseFirestore.getInstance()
    private val collectionOrderDetails = fireBase.collection("OrderDetails")
    private val collectionProduct = fireBase.collection("Product")
    private var _listOrderDetails = MutableStateFlow<List<OrderDetails>>(emptyList())
    val listOrderDatails = _listOrderDetails.asStateFlow()
    private var _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    fun getListOrderDetails(orderId: Int) {
        collectionOrderDetails.whereEqualTo("OrderId", orderId)
            .addSnapshotListener { snapshot, error ->
                _isLoading.value =true
                if (error != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val orderDetailsList = mutableListOf<OrderDetails>()
                    snapshot.forEach {
                        val orderDetails = it.toObject(OrderDetails::class.java)
                        orderDetailsList.add(orderDetails)
                    }
                    _listOrderDetails.value = orderDetailsList
                }
                _isLoading.value = false
            }
    }

    @SuppressLint("SuspiciousIndentation")
    fun getProductById(productId: Int, onComplete: (Product?) -> Unit) {
        var product = Product()
        collectionProduct.document(productId.toString())
            .get()
            .addOnSuccessListener { document ->
               if(document!=null){
                   val p = document.toObject(Product::class.java)
                     if (p != null) {
                            product = p
                            onComplete(product)
                        } else {
                            onComplete(null)
                     }
               }
            }
            .addOnFailureListener {
                onComplete(null)
            }
    }
}