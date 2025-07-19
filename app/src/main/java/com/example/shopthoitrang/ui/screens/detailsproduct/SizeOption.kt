package com.example.shopthoitrang.ui.screens.detailsproduct

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopthoitrang.R

@Composable
fun SizeOption(
    size: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = size,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) colorResource(R.color.black) else Color.LightGray)
            .clickable { onClick() }
            .padding(8.dp),
        color = if (isSelected) Color.White else Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
}
