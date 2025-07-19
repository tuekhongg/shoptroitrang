package com.example.shopthoitrang.model

import androidx.compose.ui.graphics.painter.Painter

data class NavItem(
    val title: String,
    val icon: Painter,
    val route: String
)
