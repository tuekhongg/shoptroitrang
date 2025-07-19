package com.example.shopthoitrang.ui.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.model.Order
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun OrderContent(list: List<Order>, navigate: NavHostController,shareViewModel: ShareViewModel){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(color = colorResource(R.color.white)),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(list.size) {
            val order = list[it]
            Card(modifier = Modifier.fillMaxWidth()
                .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.white)
                ),
                onClick = {
                    shareViewModel.setOrder(order)
                    navigate.navigate("orderdetails")
                }
            ){
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(8.dp)
                        .background(color = colorResource(R.color.white))
                ) {
                    Text("Mã đơn hàng: ${order.Id}")
                    Text("Thời gian đặt: ${order.OrderTime}")
                    Text("Trạng thái: ${if (order.Status==true) "Đã nhận hàng" else "Đang giao hàng"}")
                }
            }
        }
    }

}