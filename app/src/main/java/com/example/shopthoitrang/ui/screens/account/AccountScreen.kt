package com.example.shopthoitrang.ui.screens.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shopthoitrang.viewmodel.AccountViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel
import com.example.shopthoitrang.viewmodel.factory.AccountFactory

@Composable
fun AccountScreen(navigate: NavHostController, shareViewModel: ShareViewModel) {
    val user = shareViewModel.user.collectAsState().value
    val context = LocalContext.current
    if (user != null) {
        val factory = AccountFactory(shareViewModel, context)
        val accountViewModel: AccountViewModel = viewModel(factory = factory)
        UserNotNull(navigate,shareViewModel, accountViewModel)
    } else
        LoginOrSignup(navigate)
}