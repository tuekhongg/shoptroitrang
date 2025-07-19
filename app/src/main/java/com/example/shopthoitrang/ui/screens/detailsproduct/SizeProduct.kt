package com.example.shopthoitrang.ui.screens.detailsproduct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shopthoitrang.viewmodel.DetailsProductViewModel

@Composable
fun SizeProduct(listSize: List<String>, selectedSize :Int, detailsProductViewModel: DetailsProductViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        listSize.forEachIndexed { index, size ->
            SizeOption(
                size = size,
                isSelected = index == selectedSize,
                onClick = {
                    detailsProductViewModel.setSelectedSize(index)
                }
            )
        }
    }

}