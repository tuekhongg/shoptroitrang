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
    if (show) {
        val username = accountViewModel.username.collectAsState().value
        val name = accountViewModel.name.collectAsState().value
        val email = accountViewModel.email.collectAsState().value
        val phone = accountViewModel.phone.collectAsState().value
        val keyBoard = LocalSoftwareKeyboardController.current
        val context = LocalContext.current
        AlertDialog(
            onDismissRequest = {
                accountViewModel.setShowDialogUserInfo(false)
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = username,
                        onValueChange = {

                        },
                        label = {
                            Text("Tên đăng nhập")
                        },
                        readOnly = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedLabelColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            accountViewModel.setName(it)
                        },
                        label = {
                            Text("Tên người dùng")
                        },
                        maxLines = 1,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedLabelColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black
                        ),
                        keyboardActions = KeyboardActions(
                            onGo = {
                                keyBoard?.hide()
                            }
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Go
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = {

                        },
                        label = {
                            Text("Email")
                        },
                        readOnly = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedLabelColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            accountViewModel.setPhone(it)
                        },
                        label = {
                            Text("Số điện thoại")
                        },
                        maxLines = 1,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedLabelColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Go,
                            keyboardType = KeyboardType.Phone
                        ),
                        keyboardActions = KeyboardActions(
                            onGo = {
                                keyBoard?.hide()
                            }
                        )

                    )
                    Spacer(modifier = Modifier.height(16.dp))
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
                            val checkInfo = accountViewModel.checkInfo()
                            if (checkInfo == "") {
                                Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show()
                                accountViewModel.saveUserFireStore()
                            } else Toast.makeText(context, checkInfo, Toast.LENGTH_SHORT).show()
                            keyBoard?.hide()
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
}