package com.example.shopthoitrang.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.shopthoitrang.model.User
import com.example.shopthoitrang.ui.screens.account.StorageFireBase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel(val shareViewModel: ShareViewModel, val context: Context) : ViewModel() {
    private val fireBase = FirebaseFirestore.getInstance()
    private val collectionUser = fireBase.collection("User")
    private val Id = shareViewModel.user.value?.Id ?: ""
    private var _username = MutableStateFlow<String>("")
    val username = _username.asStateFlow()
    private var _name = MutableStateFlow<String>("")
    val name = _name.asStateFlow()
    private var _email = MutableStateFlow<String>("")
    val email = _email.asStateFlow()
    private var _phone = MutableStateFlow<String>("")
    val phone = _phone.asStateFlow()
    private var _avatar = MutableStateFlow<String>("")
    val avatar = _avatar.asStateFlow()
    private var _newpassword = MutableStateFlow<String>("")
    val newpassword = _newpassword.asStateFlow()
    private var _confirmnewpassword = MutableStateFlow<String>("")
    val confirmnewpassword = _confirmnewpassword.asStateFlow()
    private var _selectedUriImage = MutableStateFlow<Uri?>(null)
    val selectedUriImage = _selectedUriImage.asStateFlow()
    private var _showChangePassword = MutableStateFlow<Boolean>(false)
    val showChangePassword = _showChangePassword.asStateFlow()
    private var _showDialogName = MutableStateFlow<Boolean>(false)
    val showDialogName = _showDialogName.asStateFlow()
    private var _showDialogUserInfo = MutableStateFlow<Boolean>(false)
    val showDialogUserInfo = _showDialogUserInfo.asStateFlow()

    init {
        load()
    }

    fun load() {
        _username.value = shareViewModel.user.value?.Username ?: ""
        _name.value = shareViewModel.user.value?.Name ?: ""
        _email.value = shareViewModel.user.value?.Email ?: ""
        _phone.value = shareViewModel.user.value?.Phone ?: ""
        _avatar.value = shareViewModel.user.value?.Avatar ?: ""
    }

    fun setShowDialogUserInfo(show : Boolean){
        _showDialogUserInfo.value = show
    }

    fun setSelectedUri(uri: Uri?) {
        _selectedUriImage.value = uri
    }

    fun setShowDialogName(show: Boolean) {
        _showDialogName.value = show
    }

    fun setShowChangePassword(show: Boolean) {
        _showChangePassword.value = show
    }

    fun setNewpassword(newpassword: String) {
        _newpassword.value = newpassword
    }

    fun setConfirmnewpassword(confirmnewpassword: String) {
        _confirmnewpassword.value = confirmnewpassword
    }

    fun setPhone(phone: String) {
        _phone.value = phone
    }

    fun resetPass() {
        _newpassword.value = ""
        _confirmnewpassword.value = ""
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun checkName(): String {
        if (_name.value.trim().isEmpty()) return "Vui lòng nhập tên người dùng"
        return ""
    }

    fun checkPassword(): String {
        if (_newpassword.value.trim().isBlank()) return "Mật khẩu không được để trống"
        if (_newpassword.value.trim().length < 6) return "Mật khẩu phải ít nhất 6 ký tự"
        if (_confirmnewpassword.value.trim()
                .isBlank()
        ) return "Mật khẩu nhập lại không được để trống"
        if (_confirmnewpassword.value.trim().length < 6) return "Mật khẩu nhập lại phải ít nhất 6 ký tự"
        if (_newpassword.value.trim() != _confirmnewpassword.value.trim()) return "Mật khẩu không khớp"
        return ""
    }

    fun changePassword() {
        FirebaseAuth.getInstance().currentUser?.updatePassword(_newpassword.value)
    }

    fun saveUser() {
        shareViewModel.setUser(
            User(
                Id,
                username.value,
                name.value,
                email.value,
                phone.value,
                avatar.value
            )
        )
    }

    fun checkInfo(): String {
        if (_phone.value.isBlank()) return "Số điện thoại không được để trống"
        if (!_phone.value.matches("^[0-9]{10,11}$".toRegex())) return "Số điện thoại không hợp lệ"
        if (_name.value.isBlank()) return "Tên không được để trống"
        return ""
    }

    fun saveUserFireStore() {
        try {
            val uri = _selectedUriImage.value
            if (uri != null) {
                StorageFireBase.initSecondaryApp(context)
                StorageFireBase.uploadToProject1Storage(
                    context, uri,
                    onSuccess = { downloadUrl ->
                        _avatar.value = downloadUrl // Update avatar mới

                        val map = mapOf(
                            "Id" to Id,
                            "Username" to username.value,
                            "Name" to name.value,
                            "Email" to email.value,
                            "Phone" to phone.value,
                            "Avatar" to downloadUrl // Lưu URL mới
                        )
                        collectionUser.document(Id)
                            .update(map)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    saveUser()
                                    load()
                                }
                            }
                    },
                    onError = {
                        Log.d("error", it)
                    })
            } else {
                val map = mapOf(
                    "Id" to Id,
                    "Username" to username.value,
                    "Name" to name.value,
                    "Email" to email.value,
                    "Phone" to phone.value,
                    "Avatar" to avatar.value
                )
                collectionUser.document(Id)
                    .update(map)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveUser()
                            load()
                        }
                    }
            }
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
        }
    }

}