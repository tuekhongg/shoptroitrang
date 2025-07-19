package com.example.shopthoitrang.ui.screens.detailsproduct

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.model.Product
import com.example.shopthoitrang.room.entity.EntityCart
import com.example.shopthoitrang.viewmodel.DetailsProductViewModel
import com.example.shopthoitrang.viewmodel.factory.DetailsProductFactory
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DetailsProductScreen(
    product: Product?,
    navigate: NavHostController
) {
    if (product != null) {
        val context = LocalContext.current
        val factory = DetailsProductFactory(context)
        val viewModel: DetailsProductViewModel = viewModel(factory = factory)
        val selectedImage = viewModel.selectedImage.collectAsState().value
        val listSize = listOf("S", "M", "L", "XL")
        val selectedSize = viewModel.selectedSize.collectAsState().value
        val listImage = product.Image
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.white))
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            BackButtonProduct(navigate,viewModel,product)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(bottom = 16.dp)
            ) {
                item {
                    MainProductImage(imageUrl = listImage.getOrNull(selectedImage))
                    ImageThumbnails(
                        listImage = listImage,
                        selectedImage = selectedImage,
                        onImageClick = { index -> viewModel.setSelectedImage(index) },
                        productName = product.Name ?: ""
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    SizeProduct(listSize,selectedSize,viewModel)
                    Spacer(modifier = Modifier.height(8.dp))
                    ProductName(name = product.Name ?: "")
                    Price(product)
                    Description(product)
                }
            }
            AddToCart(product) {
                if(product.Quantity!! <0)
                    Toast.makeText(context, "Số lượng hàng không đủ", Toast.LENGTH_SHORT).show()
                else{
                    viewModel.insertCart(EntityCart(
                        IdProduct = product.Id?:0,
                        Name = product.Name ?: "",
                        Price = product.Price ?: 0.0,
                        Quantity = 1,
                        Size = listSize[selectedSize],
                        Image = listImage[0]
                    ))
                    Toast.makeText(context, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
private fun Price(product: Product) {
    Text(
        text = "Giá bán: ${formatCurrency(product.Price ?: 0.0)} VNĐ",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 4.dp)
    )
}

fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getNumberInstance(Locale("vi", "VN"))
    return format.format(amount)
}

@Composable
fun ProductName(name: String) {
    Text(
        text = name,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold)
}
@Composable
fun Description(product: Product) {
    Text(
        text = product.Description ?: "No description available",
        modifier = Modifier.padding(top = 16.dp)
    )
}
