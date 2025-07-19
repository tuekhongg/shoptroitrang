package com.example.shopthoitrang.ui.screens.search

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopthoitrang.viewmodel.SearchViewModel

@Composable
fun SearchFilterBar(searchViewModel : SearchViewModel){
    val listCategory = searchViewModel.listCategory.collectAsState().value
    val selected = searchViewModel.selectedCategory.intValue
    var interactionSource = remember {
        MutableInteractionSource()
    }
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center){
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listCategory.size) { index ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var textWidth by remember { mutableIntStateOf(0) }
                    Text(
                        listCategory[index].Name ?: "",
                        fontSize = 16.sp,
                        fontWeight = if (index == selected) FontWeight.Bold else FontWeight.Normal,
                        color = Color.Black,
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                searchViewModel.setSelectedCategory(index)
                                searchViewModel.searchProduct()
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
    }
}