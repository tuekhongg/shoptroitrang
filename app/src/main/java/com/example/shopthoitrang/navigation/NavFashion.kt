package com.example.shopthoitrang.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopthoitrang.ui.screens.account.AccountScreen
import com.example.shopthoitrang.ui.screens.cart.CartScreen
import com.example.shopthoitrang.ui.screens.detailsproduct.DetailsProductScreen
import com.example.shopthoitrang.ui.screens.favorite.FavoriteScreen
import com.example.shopthoitrang.ui.screens.home.HomeScreen
import com.example.shopthoitrang.ui.screens.login.LoginScreen
import com.example.shopthoitrang.ui.screens.order.OrderDetailsScreen
import com.example.shopthoitrang.ui.screens.order.OrderScreen
import com.example.shopthoitrang.ui.screens.register.RegisterScreen
import com.example.shopthoitrang.ui.screens.search.SearchScreen
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun NavFashion(
    navController: NavHostController,
    paddingValue: PaddingValues,
    shareViewModel: ShareViewModel,
){
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValue)
    ) {
        composable("home"){
            HomeScreen(navController, shareViewModel)
        }
        composable("favorite") {
            FavoriteScreen(navController,shareViewModel)
        }
        composable("cart"){
            CartScreen(shareViewModel,navController)
        }
        composable("account") {
            AccountScreen(navController,shareViewModel)
        }
        composable("order") {
            OrderScreen(shareViewModel,navController)
        }
        composable("orderdetails") {
            val order = shareViewModel.order.collectAsState().value
            OrderDetailsScreen(navController,order)
        }
        composable("detailsproduct") {
            val product = shareViewModel.product.collectAsState()
            DetailsProductScreen(product.value,
                navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("login") {
            LoginScreen(shareViewModel,navController)
        }
        composable("search"){
            SearchScreen(shareViewModel,navController)
        }
    }
}