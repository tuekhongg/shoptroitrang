package com.example.shopthoitrang.ui.screens.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopthoitrang.R
import com.example.shopthoitrang.viewmodel.RegisterViewModel

@Composable
fun SuccessDialogRegister(show: Boolean, registerViewModel: RegisterViewModel) {
    if (show) {
        AlertDialog(
            onDismissRequest = {
                registerViewModel.setShowSuccessDialog(false)
            },
            confirmButton = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            registerViewModel.setShowSuccessDialog(false)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.black),
                            contentColor = colorResource(R.color.white)
                        ),
                        modifier = Modifier.height(60.dp)
                            .width(160.dp)
                    ) {
                        Text(
                            "Xác nhận",
                            fontSize = 20.sp
                        )
                    }
                }
            },
            text = {
                Text(
                    "Đăng ký thành công! Giờ bạn có thể đăng nhập bằng tài khoản này.",
                    fontSize = 16.sp
                )
            }
        )
    }

}