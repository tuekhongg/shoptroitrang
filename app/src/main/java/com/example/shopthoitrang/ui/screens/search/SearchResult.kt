package com.example.shopthoitrang.ui.screens.search

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.room.entity.EntityCart
import com.example.shopthoitrang.ui.screens.home.ProductItem
import com.example.shopthoitrang.viewmodel.SearchViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun SearchResult(
    searchViewModel: SearchViewModel,
    navigate: NavHostController,
    shareViewModel: ShareViewModel
) {
    val products = searchViewModel.listProduct.collectAsState().value
    val isLoading = searchViewModel.isSearchLoading.collectAsState().value
    val noResult = searchViewModel.noResult.collectAsState().value
    val context = LocalContext.current
    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (products.isNotEmpty()) {
            val gridState = rememberLazyStaggeredGridState()
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                state = gridState,
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalItemSpacing = 8.dp,
                contentPadding = PaddingValues(8.dp)
            ) {
                items(products.size) { index ->
                    val product = products[index]
                    ProductItem(
                        product = products[index],
                        setProduct = {
                            shareViewModel.setProduct(products[index])
                            navigate.navigate("detailsproduct")
                        },
                        insertCard = {
                            searchViewModel.insertCart(
                                EntityCart(
                                    IdProduct = product.Id ?: 0,
                                    Name = product.Name ?: "",
                                    Price = product.Price ?: 0.0,
                                    Quantity = 1,
                                    Size = "S",
                                    Image = product.Image[0]
                                )
                            )
                            Toast.makeText(
                                context,
                                "Thêm vào giỏ hàng thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                       text = if(noResult)"No Result!" else "Nhập từ khóa để tìm kiếm",
                        fontSize = 16.sp,
                        color = colorResource(R.color.textnavigationunselect)
                    )
                }
        }
    }
}