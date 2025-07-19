package com.example.shopthoitrang.ui.screens.detailsproduct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopthoitrang.R
import com.example.shopthoitrang.model.Product

@Composable
fun AddToCart(product: Product, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Button(
            onClick = {
                if(product.Quantity!=0) onClick()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if(product.Quantity!! ==0) Color.LightGray else colorResource(R.color.black),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(0.5f)
                .height(60.dp)
        ) {
            Text(
                "Add to Cart",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
