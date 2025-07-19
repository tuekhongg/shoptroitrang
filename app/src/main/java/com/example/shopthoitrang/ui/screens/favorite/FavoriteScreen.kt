package com.example.shopthoitrang.ui.screens.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.viewmodel.FavoriteViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel
import com.example.shopthoitrang.viewmodel.factory.FavoriteFactory

@Composable
fun FavoriteScreen(navigate: NavHostController, shareViewModel: ShareViewModel) {
    val context = LocalContext.current
    val factory = FavoriteFactory(context)
    val favoriteViewModel: FavoriteViewModel = viewModel(factory = factory)
    LaunchedEffect(Unit) {
        favoriteViewModel.getListFavoriteProduct()
    }
    val listFavoriteProduct = favoriteViewModel.listFavoriteProduct.collectAsState().value
    val isLoading = favoriteViewModel.isLoading.collectAsState().value
    if (isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.white)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (listFavoriteProduct.isEmpty()) FavoriteEmpty(navigate)
        else FavoriteContent(listFavoriteProduct, navigate, shareViewModel, favoriteViewModel)
    }
}