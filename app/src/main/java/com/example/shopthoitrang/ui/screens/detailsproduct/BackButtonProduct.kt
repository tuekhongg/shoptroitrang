package com.example.shopthoitrang.ui.screens.detailsproduct

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.model.Product
import com.example.shopthoitrang.room.Converters
import com.example.shopthoitrang.room.entity.EntityFavoriteProduct
import com.example.shopthoitrang.viewmodel.DetailsProductViewModel

@Composable
fun BackButtonProduct(
    navigate: NavHostController,
    detailsProductViewModel: DetailsProductViewModel,
    product: Product
) {
    LaunchedEffect(Unit) {
        detailsProductViewModel.getFavoriteProductById(product.Id ?: 0)
    }
    val isFavorite = detailsProductViewModel.isFavorite.collectAsState().value
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.back),
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) { navigate.popBackStack() }
        )
        Text(
            text = "THÔNG TIN SẢN PHẨM",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Icon(
            painter = painterResource(if (isFavorite) R.drawable.favorite_select else R.drawable.favorite_unselect),
            contentDescription = null,
            modifier = Modifier
                .size(28.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    val entity = EntityFavoriteProduct(
                        Id = product.Id ?: 0,
                        Name = product.Name ?: "",
                        Price = product.Price ?: 0.0,
                        Image = Converters.fromList(product.Image),
                        Description = product.Description ?: "",
                        CategoryId = product.CategoryId ?: 0,
                        Quantity = product.Quantity ?: 0,
                    )

                    if (isFavorite) {
                        detailsProductViewModel.deleteFavoriteProduct(entity)
                    } else {
                        detailsProductViewModel.insertFavoriteProduct(entity)
                    }
                },
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(R.drawable.cart),
            contentDescription = null,
            modifier = Modifier
                .size(28.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    navigate.navigate("cart")
                },
            tint = Color.Unspecified
        )
    }
}