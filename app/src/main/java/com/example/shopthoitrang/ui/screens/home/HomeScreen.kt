package com.example.shopthoitrang.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.viewmodel.HomeViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel
import com.example.shopthoitrang.viewmodel.factory.HomeFactory

@Composable
fun HomeScreen(
    navigate: NavHostController,
    shareViewModel: ShareViewModel
) {
    val context = LocalContext.current
    val factory = HomeFactory(context)
    val homeViewModel: HomeViewModel = viewModel(factory = factory)
    val listProduct = homeViewModel.listCategory.collectAsState().value
    if (listProduct.isNotEmpty()) listProduct.forEach {
        Log.d("list", it.Name.toString())
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBarHome(homeViewModel, navigate)
            ContentHome(
                homeViewModel = homeViewModel,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .background(color = colorResource(R.color.white)),
                navigate,
                shareViewModel
            )
            BottomBarFashion(navigate, shareViewModel)
        }
    }
}