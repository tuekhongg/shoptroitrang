package com.example.shopthoitrang.ui.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shopthoitrang.R
import com.example.shopthoitrang.room.entity.EntityCart
import com.example.shopthoitrang.ui.screens.detailsproduct.formatCurrency
import com.example.shopthoitrang.viewmodel.CartViewModel

@Composable
fun CartItem(product: EntityCart, cartViewModel: CartViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 8.dp)
            .background(
                color = colorResource(R.color.white),
                shape = RoundedCornerShape(8.dp)
            ),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.Image,
                contentDescription = null,
                modifier = Modifier
                    .width(120.dp)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillWidth
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(1.0f)
                    .padding(start = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = product.Name,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "Size: ${product.Size}",
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${formatCurrency(product.Price)}₫",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Quantity(product, cartViewModel)
                Text(
                    "${formatCurrency(total(product))}₫",
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}