package com.example.shopthoitrang.ui.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.shopthoitrang.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.shopthoitrang.model.User
import com.example.shopthoitrang.viewmodel.CartViewModel

@Composable
fun CartDialog(cartViewModel: CartViewModel,user:User?) {
    val showDialog = cartViewModel.showDialog.collectAsState().value
    val success = cartViewModel.success.collectAsState().value
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                cartViewModel.setShowDialog(false)
            },
            title = { Text("Xác nhận") },
            text = { Text("Bạn có chắc chắn muốn đặt hàng không?",
                fontSize = 16.sp) },
            confirmButton = {
                Button(
                    onClick = {
                        cartViewModel.setShowDialog(false)
                        cartViewModel.addOrder(user)
                        cartViewModel.setSuccess(true)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.black),
                        contentColor = Color.White
                    )
                ) {
                    Text("Có")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        cartViewModel.setShowDialog(false)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("Không")
                }
            }
        )
    }
    if (success) {
        AlertDialog(
            onDismissRequest = {
                cartViewModel.setSuccess(false)
            },
            confirmButton = {
                Row(
                    modifier = Modifier.width(300.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            cartViewModel.setSuccess(false)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.black),
                            contentColor = Color.White
                        )
                    ) {
                        Text("OK")
                    }
                }
            },
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AnimatedSuccessIcon()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Đặt hàng thành công!", style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            },
            modifier = Modifier.width(300.dp)
        )
    }
}

@Composable
fun AnimatedSuccessIcon() {
    val scale = remember { androidx.compose.animation.core.Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = androidx.compose.animation.core.spring(
                dampingRatio = 0.5f,
                stiffness = 300f
            )
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Success",
            tint = Color.Black,
            modifier = Modifier
                .size(80.dp)
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                }
        )
    }
}
