package com.example.shopthoitrang.ui.screens.detailsproduct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ImageThumbnails(
    listImage: List<String>,
    selectedImage: Int,
    onImageClick: (Int) -> Unit,
    productName: String
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(listImage) { index, item ->
            ThumbnailImage(
                imageUrl = item,
                isSelected = index == selectedImage,
                onClick = { onImageClick(index) },
                productName = productName
            )
        }
    }
}