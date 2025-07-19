package com.example.shopthoitrang.ui.screens.account

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.shopthoitrang.viewmodel.AccountViewModel

@Composable
fun DialogInfoUser(
    show: Boolean,
    accountViewModel: AccountViewModel
) {
    if (!show) return

    val username = accountViewModel.username.collectAsState().value
    val name = accountViewModel.name.collectAsState().value
    val email = accountViewModel.email.collectAsState().value
    val phone = accountViewModel.phone.collectAsState().value

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = {
            accountViewModel.setShowDialogUserInfo(false)
        },
        text = {
            Column {
                InfoField(
                    label = "Tên đăng nhập",
                    value = username,
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                InfoField(
                    label = "Tên người dùng",
                    value = name,
                    onValueChange = accountViewModel::setName,
                    imeAction = ImeAction.Go,
                    onGo = { keyboardController?.hide() }
                )
                Spacer(modifier = Modifier.height(16.dp))

                InfoField(
                    label = "Email",
                    value = email,
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                InfoField(
                    label = "Số điện thoại",
                    value = phone,
                    onValueChange = accountViewModel::setPhone,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Go,
                    onGo = { keyboardController?.hide() }
                )
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        accountViewModel.setShowDialogUserInfo(false)
                        val result = accountViewModel.checkInfo()
                        if (result.isEmpty()) {
                            Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show()
                            accountViewModel.saveUserFireStore()
                        } else {
                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                        }
                        keyboardController?.hide()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("Lưu")
                }
            }
        },
        containerColor = Color.White
    )
}

@Composable
private fun InfoField(
    label: String,
    value: String,
    onValueChange: ((String) -> Unit)? = null,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    onGo: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange ?: {},
        label = { Text(label) },
        readOnly = readOnly,
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = if (onGo != null) {
            KeyboardActions(onGo = { onGo() })
        } else {
            KeyboardActions()
        },
        modifier = Modifier.fillMaxWidth()
    )
}
