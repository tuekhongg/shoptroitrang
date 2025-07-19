package com.example.shopthoitrang.ui.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.viewmodel.OrderViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun OrderTab3(orderViewModel: OrderViewModel,navigate: NavHostController,shareViewModel: ShareViewModel){
    val listOrderDaNhan = orderViewModel.listOrderDaNhan.collectAsState().value
    if (listOrderDaNhan.isEmpty()) {
        Column(modifier = Modifier.fillMaxSize()
            .background(color = colorResource(R.color.white)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text("Không có đơn hàng nào")
        }
    } else {
        OrderContent(listOrderDaNhan,navigate,shareViewModel)
    }
}