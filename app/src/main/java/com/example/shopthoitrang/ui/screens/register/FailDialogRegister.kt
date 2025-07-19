package com.example.shopthoitrang.ui.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopthoitrang.R
import com.example.shopthoitrang.viewmodel.RegisterViewModel

@Composable
fun FailDialogRegister(show: Boolean, registerViewModel: RegisterViewModel) {
    if (show)
        AlertDialog(
            onDismissRequest = {
            },
            confirmButton = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.error),
                        contentDescription = null,
                        modifier = Modifier.size(90.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            registerViewModel.setShowFailDialog(false)
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = colorResource(R.color.white),
                            containerColor = colorResource(R.color.black)
                        )
                    ) {
                        Text(
                            "Xác nhận",
                            fontSize = 20.sp
                        )
                    }
                }
            },
            text = {
                Text("Đăng ký thất bại, vui lòng thử lại sau",
                    fontSize = 16.sp)
            }
        )
}