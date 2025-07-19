package com.example.shopthoitrang.ui.screens.detailsproduct

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shopthoitrang.R

@Composable
fun ThumbnailImage(
    imageUrl: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    productName: String
) {
    Box(
        modifier = Modifier
            .then(
                if (isSelected) Modifier.border(
                    width = 1.dp,
                    color = colorResource(R.color.black),
                    shape = RoundedCornerShape(8.dp)
                ) else Modifier
            )
            .clip(RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = productName,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .wrapContentHeight()
                .width(if (isSelected) 50.dp else 45.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onClick() }
        )
    }
}