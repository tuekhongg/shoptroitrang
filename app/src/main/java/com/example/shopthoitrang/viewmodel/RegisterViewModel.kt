package com.example.shopthoitrang.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val collectionUser = db.collection("User")
    private var _name = MutableStateFlow<String>("")
    val name = _name.asStateFlow()
    private var _username = MutableStateFlow<String>("")
    val username = _username.asStateFlow()
    private var _email = MutableStateFlow<String>("")
    val email = _email.asStateFlow()
    private var _phone = MutableStateFlow<String>("")
    val phone = _phone.asStateFlow()
    private var _password = MutableStateFlow<String>("")
    val password = _password.asStateFlow()
    private var _confirmpassword = MutableStateFlow<String>("")
    val confirmpassword = _confirmpassword.asStateFlow()
    private var _showDialogSendEmail = MutableStateFlow<Boolean>(false)
    val showDialogSendEmail = _showDialogSendEmail.asStateFlow()
    private var _showFailDialog = MutableStateFlow<Boolean>(false)
    val showFailDialog = _showFailDialog.asStateFlow()
    private var _showSuccessDialog = MutableStateFlow<Boolean>(false)
    val showSuccessDialog = _showSuccessDialog.asStateFlow()
    private var _countdown = MutableStateFlow<Int>(180)
    val countdown = _countdown.asStateFlow()
    private var countdownJob: Job? = null
    fun setName(name: String) {
        _name.value = name
    }

    fun setUserName(username: String) {
        _username.value = username
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPhone(phone: String) {
        _phone.value = phone
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setConfirmPassword(confirmpassword: String) {
        _confirmpassword.value = confirmpassword
    }

    fun setShowDialogSendEmail(show: Boolean) {
        _showDialogSendEmail.value = show
    }

    fun setShowFailDialog(show: Boolean) {
        _showFailDialog.value = show
    }

    fun setShowSuccessDialog(show: Boolean) {
        _showSuccessDialog.value = show
    }

    fun startCountdown(
        onVerified: () -> Unit,
        onNotVerified: () -> Unit
    ) {
        countdownJob?.cancel()
        _countdown.value = 180
        countdownJob = viewModelScope.launch {
            while (_countdown.value > 0) {
                delay(1000)
                _countdown.value = _countdown.value - 1
                if (_countdown.value % 5 == 0) {
                    reloadUserAndCheckVerified(
                        onVerified = {
                            onVerified()
                            countdownJob?.cancel() // Dừng job
                        },
                        onNotVerified = {

                        }
                    )
                }
            }
            setShowDialogSendEmail(false)
            deleteUser()
            onNotVerified()
        }
    }

    fun stopCountdown() {
        countdownJob?.cancel()
    }

    fun deleteUser() {
        auth.currentUser?.delete()
    }

    fun checkUsername(onResult: (Boolean) -> Unit) {
        collectionUser.whereEqualTo("Username", username.value).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.isEmpty) {
                    onResult(false)
                } else {
                    onResult(true)
                }
            }
            .addOnFailureListener {
                onResult(true)
            }
    }

    fun checkInfo(): String {
        val nameValue = name.value.trim()
        val usernameValue = username.value.trim()
        val emailValue = email.value.trim()
        val phoneValue = phone.value.trim()
        val passwordValue = password.value.trim()
        val confirmPasswordValue = confirmpassword.value.trim()

        if (nameValue.isBlank()) return "Tên người dùng không được để trống"
        if (usernameValue.isBlank()) return "Tên đăng nhập không được để trống"
        if (!usernameValue.matches("^[a-zA-Z0-9_]+$".toRegex())) return "Tên đăng nhập chỉ được chứa chữ, số và dấu gạch dưới"
        if (emailValue.isBlank()) return "Email không được để trống"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue)
                .matches()
        ) return "Email không đúng định dạng"
        if (phoneValue.isBlank()) return "Số điện thoại không được để trống"
        if (!phoneValue.matches("^[0-9]{10,11}$".toRegex())) return "Số điện thoại không hợp lệ"
        if (passwordValue.isBlank()) return "Mật khẩu không được để trống"
        if (passwordValue.length < 6) return "Mật khẩu phải ít nhất 6 ký tự"
        if (confirmPasswordValue.isBlank()) return "Vui lòng nhập lại mật khẩu"
        if (passwordValue != confirmPasswordValue) return "Mật khẩu không khớp"
        return ""
    }


    fun registerUserWithEmailVerification(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnSuccessListener {
                            onSuccess()
                        }
                        ?.addOnFailureListener { e ->
                            onError("Gửi email xác minh thất bại: ${e.message}")
                        }
                } else {
                    onError("Đăng ký thất bại: ${task.exception?.message}")
                }
            }
    }

    fun saveUserInfoToFirestore(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val userMap = hashMapOf(
                "Id" to uid,
                "Name" to _name.value,
                "Username" to _username.value,
                "Email" to _email.value,
                "Phone" to _phone.value,
                "Avatar" to ""
            )
            collectionUser.document(uid)
                .set(userMap)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    onError("Lưu Firestore thất bại: ${e.message}")
                }
        } else {
            onError("Không tìm thấy UID")
        }
    }

    fun reloadUserAndCheckVerified(
        onVerified: () -> Unit,
        onNotVerified: () -> Unit
    ) {
        val user = auth.currentUser
        user?.reload()
            ?.addOnSuccessListener {
                if (user.isEmailVerified) {
                    onVerified()
                } else {
                    onNotVerified()
                }
            }
    }
}
