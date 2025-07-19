package com.example.shopthoitrang.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopthoitrang.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val fireBase = FirebaseFirestore.getInstance()
    private val collectionUser = fireBase.collection("User")
    private var _username = MutableStateFlow<String>("")
    val username = _username.asStateFlow()
    private var _password = MutableStateFlow<String>("")
    val password = _password.asStateFlow()
    private var _showPassword = MutableStateFlow<Boolean>(false)
    val showPassword = _showPassword.asStateFlow()
    private var _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()
    fun setIsloading(load: Boolean){
        _isLoading.value = load
    }
    fun setUsername(username: String) {
        _username.value = username
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setShowPassword(show: Boolean) {
        _showPassword.value = show
    }

    fun checkInfo(): String {
        if (_username.value.trim().isBlank() || !username.value.trim()
                .matches("^[a-zA-Z0-9_]+$".toRegex())
        )
            return "Tên đăng nhập không hợp lệ "
        if (_password.value.trim().isBlank()) return "Mật khẩu không được để trống"
        return ""
    }
    fun loginWithUsernameAndPassword(onSuccess: (user: User) -> Unit, onError: () -> Unit) {
            _isLoading.value = true
            try {
                collectionUser.whereEqualTo("Username", _username.value.trim())
                    .limit(1)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        if (!snapshot.isEmpty) {
                            val user = snapshot.documents[0].toObject(User::class.java)
                            if (!user?.Email.isNullOrEmpty()) {
                                auth.signInWithEmailAndPassword(user.Email, _password.value)
                                    .addOnCompleteListener { task ->
                                        _isLoading.value = false
                                        if (task.isSuccessful) {
                                            onSuccess(user)
                                        } else {
                                            onError()
                                        }
                                    }
                                    .addOnFailureListener {
                                        onError()
                                    }
                            } else {
                                onError()
                            }
                        } else {
                            onError()
                        }
                    }
                    .addOnFailureListener {
                        Log.d("LoginViewModel", "Lỗi Firestore: ${it.message}")
                        onError()
                    }
            } catch (e: Exception) {
                _isLoading.value = false
                Log.d("LoginViewModel", "Exception: ${e.message}")
                onError()
            }
        }


}