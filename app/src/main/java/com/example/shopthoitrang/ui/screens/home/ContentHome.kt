package com.example.shopthoitrang.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.shopthoitrang.R
import com.example.shopthoitrang.model.Product
import com.example.shopthoitrang.room.entity.EntityCart
import com.example.shopthoitrang.ui.screens.detailsproduct.formatCurrency
import com.example.shopthoitrang.viewmodel.HomeViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun ContentHome(
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    navigate: NavHostController,
    shareViewModel: ShareViewModel
) {
    val products = homeViewModel.listProduct.collectAsState().value
    ProductGrid(
        products = products,
        modifier = modifier,
        navigate = navigate,
        shareViewModel = shareViewModel,
        homeViewModel
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductGrid(
    products: List<Product>,
    modifier: Modifier = Modifier,
    navigate: NavHostController,
    shareViewModel: ShareViewModel,
    homeViewModel: HomeViewModel
) {
    val gridState = rememberLazyStaggeredGridState()
    val context = LocalContext.current
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        state = gridState,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp,
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products.size) { index ->
            val product = products[index]
            ProductItem(
                product = product,
                setProduct = {
                    shareViewModel.setProduct(product)
                    navigate.navigate("detailsproduct")
                },
                insertCard = {
                    homeViewModel.insertCart(
                        EntityCart(
                            IdProduct = product.Id?:0,
                            Name = product.Name?:"",
                            Price = product.Price?:0.0,
                            Quantity = 1,
                            Size = "S",
                            Image = product.Image[0]
                        )
                    )
                    Toast.makeText(context,"Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}


@Composable
fun ProductItem(
    product: Product,
    setProduct: () -> Unit,
    insertCard: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = (screenWidth - 32.dp) / 2  // padding 8dp x 2 + spacing
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .width(itemWidth)
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(8.dp)
                .clickable {
                    setProduct()
                }) {
            AsyncImage(
                model = product.Image.firstOrNull(),
                contentDescription = product.Name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                error = painterResource(R.drawable.noimage),
                placeholder = painterResource(R.drawable.noimage)
            )
            Text(
                text = product.Name ?: "",
                maxLines = 1,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "${formatCurrency(product.Price ?: 0.0)}₫",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 2.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = {
                        insertCard()
                    }
                ) {
                    Text(
                        "Add to cart",
                        color = Color.Black
                    )
                }
            }
        }
    }
}
