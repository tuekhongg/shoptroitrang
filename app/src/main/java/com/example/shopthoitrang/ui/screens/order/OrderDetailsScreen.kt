package com.example.shopthoitrang.ui.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.model.Order
import com.example.shopthoitrang.viewmodel.OrderDetailsViewModel

@Composable
fun OrderDetailsScreen(navigate: NavHostController, order: Order?) {
    if (order != null) {
        val orderDetailsViewModel: OrderDetailsViewModel = viewModel()
        val isLoading = orderDetailsViewModel.isLoading.collectAsState().value
        LaunchedEffect(Unit) {
            orderDetailsViewModel.getListOrderDetails(order.Id ?: 1)
        }
        val listOrderDetails = orderDetailsViewModel.listOrderDatails.collectAsState().value
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.white))
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            OrderHeader("CHI TIẾT ĐƠN HÀNG: ${order.Id}") {
                navigate.popBackStack()
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if (listOrderDetails.isNotEmpty()) {
                 OrderDetailsContent(listOrderDetails,orderDetailsViewModel)
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Không có sản phẩm nào trong đơn hàng này",
                            color = colorResource(R.color.black)
                        )
                    }
                }
            }
        }
    }
}

