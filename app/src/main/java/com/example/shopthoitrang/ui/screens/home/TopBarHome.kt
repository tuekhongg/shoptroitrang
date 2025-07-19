package com.example.shopthoitrang.ui.screens.home

import androidx.compose.foundation.Canvas
import com.example.shopthoitrang.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shopthoitrang.viewmodel.HomeViewModel

@Composable
fun TopBarHome(homeViewModel: HomeViewModel,navigate: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .background(color = colorResource(R.color.white))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                .background(color = colorResource(R.color.white)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CategoryBar(homeViewModel, navigate)
        }
    }
}

@Composable
fun CategoryBar(homeViewModel: HomeViewModel, navigate: NavHostController) {
    val list = homeViewModel.listCategory.collectAsState().value
    val selected = homeViewModel.selectedCategory.intValue
    val interactionSource = remember {
        MutableInteractionSource()
    }
    if (list.isNotEmpty())
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(list.size) { index ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var textWidth by remember { mutableStateOf(0) }
                        Text(
                            list[index].Name ?: "",
                            fontSize = 16.sp,
                            fontWeight = if (index == selected) FontWeight.Bold else FontWeight.Normal,
                            color = Color.Black,
                            modifier = Modifier
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    homeViewModel.setSelectedCategory(index)
                                    homeViewModel.loadProduct()
                                }
                                .onGloballyPositioned { coordinates ->
                                    textWidth = coordinates.size.width
                                }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        if (index == selected) {
                            val density = LocalDensity.current
                            Canvas(
                                modifier = Modifier
                                    .width(with(density) { (textWidth*0.7f).toDp() })
                                    .height(5.dp)
                            ) {
                                drawLine(
                                    start = Offset(0f, size.height / 2f),
                                    end = Offset(size.width, size.height / 2f),
                                    strokeWidth = 10.0f,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ){
                        navigate.navigate("search")
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(R.drawable.cart),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ){
                        navigate.navigate("cart")
                    },
                tint = Color.Unspecified
            )
        }
    Spacer(modifier = Modifier.height(8.dp))
}
