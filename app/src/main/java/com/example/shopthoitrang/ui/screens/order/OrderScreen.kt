package com.example.shopthoitrang.ui.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.example.shopthoitrang.viewmodel.OrderViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun OrderScreen(shareViewModel: ShareViewModel, navigate: NavHostController) {
    val user = shareViewModel.user.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        OrderHeader("ĐƠN HÀNG") {
            navigate.popBackStack()
        }
        if (user == null)  {
          Column(
              modifier = Modifier.fillMaxSize(),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
          ) {
              Text("Vui lòng đăng nhập để xem đơn hàng")
          }
        }
        else {
            val orderViewModel: OrderViewModel = viewModel()
            LaunchedEffect(Unit) {
                orderViewModel.getListOrder(user.Username)
            }
            MyTabLayout(orderViewModel,navigate,shareViewModel)
        }
    }
}