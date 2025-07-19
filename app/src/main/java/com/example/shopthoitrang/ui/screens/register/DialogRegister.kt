package com.example.shopthoitrang.ui.screens.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopthoitrang.viewmodel.RegisterViewModel

@Composable
fun DialogRegister(show: Boolean, registerViewModel: RegisterViewModel) {
    var countdown = registerViewModel.countdown.collectAsState().value
    if (show) {
        LaunchedEffect(show) {
            registerViewModel.startCountdown(
                onVerified = {
                    registerViewModel.setShowDialogSendEmail(false)
                    registerViewModel.saveUserInfoToFirestore(
                        onSuccess = {
                            registerViewModel.setShowSuccessDialog(true)
                        },
                        onError = {
                            registerViewModel.setShowFailDialog(true)
                        }
                    )
                },
                onNotVerified = {
                    registerViewModel.setShowFailDialog(true)
                }
            )
        }

        AlertDialog(
            onDismissRequest = {
            },
            confirmButton = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "( ${countdown} )",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            registerViewModel.startCountdown(
                                onVerified = {
                                    registerViewModel.setShowDialogSendEmail(false)
                                    registerViewModel.saveUserInfoToFirestore(
                                        onSuccess = {
                                            registerViewModel.setShowSuccessDialog(true)
                                        },
                                        onError = {
                                            registerViewModel.setShowFailDialog(true)
                                        }
                                    )
                                },
                                onNotVerified = {
                                    registerViewModel.setShowFailDialog(true)
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Gửi lại mã")
                    }
                    Button(
                        onClick = {
                            registerViewModel.stopCountdown()
                            registerViewModel.setShowDialogSendEmail(false)
                            registerViewModel.deleteUser()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Hủy yêu cầu")
                    }
                }
            },
            text = {
                Text("Chúng tôi đã gửi thông báo xác thực đến ${registerViewModel.email.collectAsState().value}, Vui lòng xác thực nếu Email là của bạn",
                    fontSize = 16.sp)
            }
        )
    }
}