package com.example.shopthoitrang.ui.screens.cart

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.room.entity.EntityCart
import com.example.shopthoitrang.ui.screens.detailsproduct.formatCurrency
import com.example.shopthoitrang.viewmodel.CartViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun CartContent(
    shareViewModel: ShareViewModel,
    listCart: List<EntityCart>,
    cartViewModel: CartViewModel,
    navigate: NavHostController
) {
    val user = shareViewModel.user.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val location = cartViewModel.location.collectAsState().value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .background(color = colorResource(R.color.white)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderCart(navigate)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(listCart.size) {
                    val product = listCart[it]
                    CartItem(product, cartViewModel)
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        drawLine(
                            start = Offset(0f, size.height / 2),
                            end = Offset(size.width, size.height / 2),
                            color = Color.LightGray,
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Tổng tiền: ${formatCurrency(listCart.sumOf { total(it) })}₫",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = location,
                        onValueChange = {
                            cartViewModel.setLocation(it)
                        },
                        minLines = 4,
                        maxLines = 4,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        label = {
                            Text("Địa chỉ nhận hàng")
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedBorderColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Go
                        ),
                        keyboardActions = KeyboardActions(
                            onGo = {
                                keyboardController?.hide()
                            }
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            if (cartViewModel.checkInfo()) {
                                if (user != null)
                                    cartViewModel.setShowDialog(true)
                                else
                                    Toast.makeText(
                                        context,
                                        "Vui lòng đăng nhập để đặt hàng",
                                        Toast.LENGTH_SHORT
                                    ).show()
                            } else
                                Toast.makeText(
                                    context,
                                    "Vui lòng điền đầy đủ thông tin",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(top = 16.dp, bottom = 32.dp)
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.black),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            "Đặt hàng",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        )
                    }
                }
            }
        }
    }
    CartDialog(cartViewModel,user)
}