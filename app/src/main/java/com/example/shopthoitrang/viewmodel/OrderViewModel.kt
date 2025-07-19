package com.example.shopthoitrang.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.shopthoitrang.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderViewModel() : ViewModel() {
    private val fireBase = FirebaseFirestore.getInstance()
    private val collectionOrder = fireBase.collection("Order")
    private val collectionOrderDetails = fireBase.collection("OrderDetails")
    private var _listOrder = MutableStateFlow<List<Order>>(emptyList())
    val listOrder = _listOrder.asStateFlow()
    private var _listOrderDangGiao = MutableStateFlow<List<Order>>(emptyList())
    val listOrderDangGiao = _listOrderDangGiao.asStateFlow()
    private var _listOrderDaNhan = MutableStateFlow<List<Order>>(emptyList())
    val listOrderDaNhan = _listOrderDaNhan.asStateFlow()
    private var _selectedTabIndex = MutableStateFlow<Int>(0)
    val selectedTabIndex = _selectedTabIndex.asStateFlow()

    fun setSelectedTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }
    fun getListOrder(username:String) {
       try {
           collectionOrder.whereEqualTo("UserId",username)
               .addSnapshotListener { snapshot, error ->
                   if (error != null) {
                       return@addSnapshotListener
                   }
                   if (snapshot != null) {
                       var Orders = mutableListOf<Order>()
                       var OrdersDangGiao = mutableListOf<Order>()
                       var OrdersDaNhan = mutableListOf<Order>()
                       snapshot.forEach {
                           val order = it.toObject(Order::class.java)
                           Orders.add(order)
                           if (order.Status == false)
                               OrdersDangGiao.add(order)
                           else
                               OrdersDaNhan.add(order)
                       }
                       _listOrder.value = Orders
                       _listOrderDangGiao.value = OrdersDangGiao
                       _listOrderDaNhan.value = OrdersDaNhan
                   }
               }
       }
       catch (e: Exception){
           Log.d("order",e.message.toString())
       }
    }
}