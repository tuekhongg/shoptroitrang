package com.example.shopthoitrang.ui.screens.favorite

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.model.Product
import com.example.shopthoitrang.room.Converters
import com.example.shopthoitrang.room.entity.EntityCart
import com.example.shopthoitrang.room.entity.EntityFavoriteProduct
import com.example.shopthoitrang.ui.screens.home.ProductItem
import com.example.shopthoitrang.viewmodel.FavoriteViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun FavoriteContent(
    listFavoriteProduct: List<EntityFavoriteProduct>,
    navigate: NavHostController,
    shareViewModel: ShareViewModel,
    favoriteViewModel: FavoriteViewModel
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = colorResource(R.color.white))
    ){
        Spacer(modifier = Modifier.height(16.dp))
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (icon,text) = createRefs()
            Icon(
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                modifier = Modifier.constrainAs(icon) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                    .clickable(
                        indication =null,
                        interactionSource = interactionSource
                    ){
                      navigate.popBackStack()
                    }
            )
            Text("YÊU THÍCH",
                modifier = Modifier.constrainAs(text) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.black)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        val gridState = rememberLazyStaggeredGridState()
        val context = LocalContext.current
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            state = gridState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 8.dp,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp)
                .background(color = colorResource(R.color.white))
        ) {
            items(listFavoriteProduct.size) { index ->
                val product = Product(
                    Id = listFavoriteProduct[index].Id,
                    Name = listFavoriteProduct[index].Name,
                    Price = listFavoriteProduct[index].Price,
                    Image = Converters.toList(listFavoriteProduct[index].Image),
                    Description = listFavoriteProduct[index].Description,
                    CategoryId = listFavoriteProduct[index].CategoryId,
                    Quantity = listFavoriteProduct[index].Quantity
                )
                ProductItem(
                    product = product,
                    setProduct = {
                        shareViewModel.setProduct(product)
                        navigate.navigate("detailsproduct")
                    },
                    insertCard = {
                    favoriteViewModel.insertCart(
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
}