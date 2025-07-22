package com.example.shopthoitrang.ui.screens.cart

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopthoitrang.room.entity.EntityCart
import com.example.shopthoitrang.viewmodel.CartViewModel

@Composable
fun Quantity(entityCart: EntityCart, cartViewModel: CartViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(
            false
        ) {
            if (entityCart.Quantity > 1) {
                entityCart.Quantity -= 1
                cartViewModel.updateCart(entityCart)
            } else {
                cartViewModel.deteleCart(entityCart)
            }
        }
        Text(
            text = "${entityCart.Quantity}",
            modifier = Modifier
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        ActionButton(
            true
        ) {
            entityCart.Quantity += 1
            cartViewModel.updateCart(entityCart)
        }
    }
}