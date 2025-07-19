package com.example.shopthoitrang.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopthoitrang.model.Order
import com.example.shopthoitrang.model.OrderDetails
import com.example.shopthoitrang.model.User
import com.example.shopthoitrang.room.database.DatabaseShop
import com.example.shopthoitrang.room.entity.EntityCart
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CartViewModel(context: Context) : ViewModel() {
    private val fireBase = FirebaseFirestore.getInstance()
    private val collectionOrder = fireBase.collection("Order")
    private val collectionOrderDetails = fireBase.collection("OrderDetails")
    private val daoCart = DatabaseShop.getInstance(context).daoCart()
    private var _listCart = MutableStateFlow<List<EntityCart>>(emptyList())
    val listCart = _listCart.asStateFlow()
    private var _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()
    private var _location = MutableStateFlow<String>("")
    val location = _location.asStateFlow()
    private var _phone = MutableStateFlow<String>("")
    val phone = _phone.asStateFlow()
    private var _toPerson = MutableStateFlow<String>("")
    val toPerson = _toPerson.asStateFlow()
    private var _showDialog = MutableStateFlow<Boolean>(false)
    val showDialog = _showDialog.asStateFlow()
    private var _success = MutableStateFlow<Boolean>(false)
    val success = _success.asStateFlow()
    init {
        getAll()
    }
    fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getAll() {
        _isLoading.value = true
        viewModelScope.launch {
            _listCart.value = daoCart.getAll()
            _isLoading.value = false
        }
    }

    fun updateCart(entityCart: EntityCart) {
        viewModelScope.launch {
            daoCart.updateCart(entityCart)
            _listCart.value = daoCart.getAll()
        }
    }
    fun deteleCart(entityCart: EntityCart){
        viewModelScope.launch {
            daoCart.deleteCart(entityCart)
            _listCart.value = daoCart.getAll()
        }
    }
    fun setLocation(location :String){
        _location.value = location
    }
    fun setPhone(phone: String) {
        _phone.value = phone
    }
    fun checkInfo(): Boolean{
        return _location.value.isNotEmpty()
    }
    fun setPerson(toPerson: String) {
        _toPerson.value = toPerson
    }
    fun setShowDialog(show: Boolean) {
        _showDialog.value = show
    }
    fun setSuccess(success: Boolean) {
        _success.value = success
    }
    fun deleteAll(){
        viewModelScope.launch {
            _listCart.value.forEach {
                daoCart.deleteCart(it)
            }
            _listCart.value = emptyList()
        }
    }
    fun resetAll(){
        _location.value = ""
        _phone.value = ""
        _toPerson.value = ""
    }
    fun addOrder(user:User?) {
        // Lấy OrderId mới
        collectionOrder.orderBy("Id")
            .limitToLast(1)
            .get()
            .addOnSuccessListener { snapshotOrder ->
                var newId = 1
                snapshotOrder.forEach {
                    newId = it.toObject(Order::class.java).Id!! + 1
                }

                // Tạo map Order
                val mapOrder = mapOf(
                    "Id" to newId,
                    "UserId" to user?.Username,
                    "Name" to user?.Name,
                    "Phone" to user?.Phone,
                    "Location" to _location.value,
                    "OrderTime" to getCurrentTime(),
                    "Status" to false
                )

                // Lưu Order
                collectionOrder.document(newId.toString())
                    .set(mapOrder)
                    .addOnSuccessListener {
                        resetAll()

                        // Lấy Id cuối cùng của OrderDetails
                        collectionOrderDetails.orderBy("Id")
                            .limitToLast(1)
                            .get()
                            .addOnSuccessListener { snapshotDetail ->
                                var newIdDetail = 1
                                snapshotDetail.forEach {
                                    newIdDetail = it.toObject(OrderDetails::class.java).Id!! + 1
                                }

                                var completedCount = 0
                                val totalDetails = _listCart.value.size

                                // Thêm từng OrderDetails
                                _listCart.value.forEach { productCart ->
                                    val mapDetail = mapOf(
                                        "Id" to newIdDetail,
                                        "OrderId" to newId,
                                        "ProductId" to productCart.IdProduct,
                                        "Price" to productCart.Price,
                                        "Quantity" to productCart.Quantity,
                                        "Size" to productCart.Size
                                    )

                                    collectionOrderDetails.document(newIdDetail.toString())
                                        .set(mapDetail)
                                        .addOnSuccessListener {
                                            completedCount++
                                            if (completedCount == totalDetails) {
                                                deleteAll()
                                            }
                                        }
                                    newIdDetail++ // tăng Id cho OrderDetails tiếp theo
                                }
                            }
                    }
            }
    }

}