package com.example.shopthoitrang.ui.screens.cart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(isPlus: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .clip(CircleShape)
            .background(color = Color.LightGray, CircleShape)
            .size(30.dp)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier.size(12.dp)
        ) {
            if (isPlus)
                drawLine(
                    color = Color.Black,
                    start = Offset(
                        size.width / 2,
                        0f
                    ),
                    end = Offset(
                        size.width / 2,
                        size.height
                    ),
                    strokeWidth = 8f
                )
            drawLine(
                color = Color.Black,
                start = Offset(0f, size.height / 2),             // Bắt đầu từ trái
                end = Offset(size.width, size.height / 2),       // Kết thúc ở phải
                strokeWidth = 8f
            )
        }
    }
}