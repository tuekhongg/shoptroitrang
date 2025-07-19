package com.example.shopthoitrang

import android.os.Build
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.shopthoitrang.navigation.NavFashion
import com.example.shopthoitrang.ui.theme.ShopthoitrangTheme
import com.example.shopthoitrang.viewmodel.ShareViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setNavigationBarColor()
        setContent {
            ShopthoitrangTheme {
                val navController = rememberNavController()
                val shareViewModel: ShareViewModel = viewModel()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavFashion(navController = navController,
                        innerPadding,
                        shareViewModel
                        )
                }
            }
        }
    }
    private fun setNavigationBarColor() {
        window.navigationBarColor = Color.BLACK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.decorView.systemUiVisibility = 0
        }
    }
}

