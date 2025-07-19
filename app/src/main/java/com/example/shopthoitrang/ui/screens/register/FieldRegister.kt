package com.example.shopthoitrang.ui.screens.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import com.example.shopthoitrang.viewmodel.RegisterViewModel

@Composable
fun FieldRegister(viewModel: RegisterViewModel) {
    val name = viewModel.name.collectAsState().value
    val username = viewModel.username.collectAsState().value
    val email = viewModel.email.collectAsState().value
    val phone = viewModel.phone.collectAsState().value
    val password = viewModel.password.collectAsState().value
    val confirmPassword = viewModel.confirmpassword.collectAsState().value
    val keyBoard = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldRegister(
            name,
            "Tên người dùng",
            Icons.Default.Person,
            keyBoard,
            KeyboardType.Text,
            false
        ) {
            viewModel.setName(it)
        }
        TextFieldRegister(
            username,
            "Tên đăng nhập",
            Icons.Default.Person,
            keyBoard,
            KeyboardType.Text,
            false
        ) {
            viewModel.setUserName(it)
        }
        TextFieldRegister(
            email,
            "Email",
            Icons.Default.Email,
            keyBoard,
            KeyboardType.Email,
            false
        ) {
            viewModel.setEmail(it)
        }
        TextFieldRegister(
            phone,
            "Số điện thoại",
            Icons.Default.Phone,
            keyBoard,
            KeyboardType.Text,
            false
        ) {
            viewModel.setPhone(it)
        }
        TextFieldRegister(
            password,
            "Mật khẩu",
            Icons.Default.Lock,
            keyBoard,
            KeyboardType.Password,
            true
        ) {
            viewModel.setPassword(it)
        }
        TextFieldRegister(
            confirmPassword,
            "Nhập lại mật khẩu",
            Icons.Default.Lock,
            keyBoard,
            KeyboardType.Password,
            true
        ) {
            viewModel.setConfirmPassword(it)
        }
    }
}