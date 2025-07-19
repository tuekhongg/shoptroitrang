package com.example.shopthoitrang.ui.screens.order

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shopthoitrang.R
import com.example.shopthoitrang.model.OrderDetails
import com.example.shopthoitrang.ui.screens.detailsproduct.formatCurrency
import com.example.shopthoitrang.viewmodel.OrderDetailsViewModel

@Composable
fun OrderDetailsContent(listOrderDetails: List<OrderDetails>, orderDetailsViewModel: OrderDetailsViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
    ) {
        items(listOrderDetails.size) {
            index->
            val orderDetails = listOrderDetails[index]
            OrderDetailsItem(orderDetails = orderDetails, viewModel = orderDetailsViewModel)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Canvas(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 6.dp)
            ) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Tổng tiền: ${formatCurrency(TongTien(listOrderDetails))}₫",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@Composable
fun OrderDetailsItem(orderDetails: OrderDetails, viewModel: OrderDetailsViewModel) {
    // Tạo state riêng để lưu tên sản phẩm cho từng item
    val productName = remember { mutableStateOf("Đang tải...") }
    val image = remember {
        mutableStateOf("")
    }
    LaunchedEffect(orderDetails.ProductId) {
        viewModel.getProductById(orderDetails.ProductId ?: 0) { product ->
            if (product != null) {
                productName.value = product.Name.toString()
                image.value = product.Image[0]
            } else {
                productName.value = "Không tìm thấy"
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(color = colorResource(R.color.white))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = image.value,
                contentDescription = null,
                modifier = Modifier
                    .width(120.dp)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillWidth,
                error = painterResource(R.drawable.ic_launcher_foreground)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = colorResource(R.color.white))
            ) {
                Text(productName.value,
                    overflow = TextOverflow.Ellipsis, maxLines = 1)
                Text("Size: ${orderDetails.Size}")
                Text("Số lượng: ${orderDetails.Quantity}")
                Text("Giá: ${formatCurrency(orderDetails.Price?:0.0)}₫")
                 val tongtien = (orderDetails.Price ?: 0.0) * (orderDetails.Quantity ?: 0)
                Text("Tổng tiền: ${formatCurrency(tongtien)}₫")
            }
        }
    }
}
fun TongTien(list: List<OrderDetails>): Double{
    var tongtien = 0.0
    for (item in list) {
        val price = item.Price ?: 0.0
        val quantity = item.Quantity ?: 0
        tongtien += price * quantity
    }
    return tongtien
}