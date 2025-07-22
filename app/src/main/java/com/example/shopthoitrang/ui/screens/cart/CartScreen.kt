package com.example.shopthoitrang.ui.screens.cart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.room.entity.EntityCart
import com.example.shopthoitrang.viewmodel.CartViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel
import com.example.shopthoitrang.viewmodel.factory.CartFactory

@Composable
fun CartScreen(shareViewModel: ShareViewModel, navigate : NavHostController) {
    val context = LocalContext.current
    val factory = CartFactory(context)
    val cartViewModel: CartViewModel = viewModel(factory = factory)
    val listCart = cartViewModel.listCart.collectAsState().value
    val isLoading = cartViewModel.isLoading.collectAsState().value
    LaunchedEffect(Unit) {
        cartViewModel.getAll()
    }

    if (isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.white)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (listCart.isEmpty()) EmptyCart(navigate)
        else CartContent(shareViewModel, listCart, cartViewModel,navigate)
    }
}

@Composable
fun total(entityCart: EntityCart): Double {
    return entityCart.Price * entityCart.Quantity
}


