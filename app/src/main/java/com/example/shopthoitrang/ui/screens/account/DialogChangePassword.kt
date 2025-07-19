package com.example.shopthoitrang.ui.screens.account

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopthoitrang.R
import com.example.shopthoitrang.viewmodel.AccountViewModel

@Composable
fun DialogChangePassword(
    show: Boolean,
    accountViewModel: AccountViewModel
) {
    if (!show) return

    val newPassword = accountViewModel.newpassword.collectAsState().value
    val confirmPassword = accountViewModel.confirmnewpassword.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { },
        title = {
            Text("Đổi mật khẩu", fontSize = 16.sp)
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                PasswordField(
                    label = "Mật khẩu mới",
                    value = newPassword,
                    onValueChange = accountViewModel::setNewpassword,
                    keyboardController = keyboardController
                )
                Spacer(modifier = Modifier.height(12.dp))
                PasswordField(
                    label = "Nhập lại mật khẩu mới",
                    value = confirmPassword,
                    onValueChange = accountViewModel::setConfirmnewpassword,
                    keyboardController = keyboardController
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val error = accountViewModel.checkPassword()
                    if (error.isEmpty()) {
                        accountViewModel.changePassword()
                        Toast.makeText(context, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show()
                        accountViewModel.setShowChangePassword(false)
                        accountViewModel.resetPass()
                        keyboardController?.hide()
                    } else {
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.black),
                    contentColor = colorResource(R.color.white)
                )
            ) {
                Text("Lưu")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    accountViewModel.setShowChangePassword(false)
                    accountViewModel.resetPass()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.black),
                    contentColor = colorResource(R.color.white)
                )
            ) {
                Text("Hủy")
            }
        },
        containerColor = Color.White
    )
}

@Composable
private fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        label = { Text(label) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null)
        },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions(
            onGo = { keyboardController?.hide() }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
