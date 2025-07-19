package com.example.shopthoitrang.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.viewmodel.LoginViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun LoginScreen(shareViewModel: ShareViewModel, navigate: NavHostController) {
    val loginViewModel: LoginViewModel = viewModel()
    val interactionSource = remember {
        MutableInteractionSource()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HeaderLogin(navigate)
            Spacer(modifier = Modifier.height(8.dp))
            FieldLogin(loginViewModel)
            Spacer(modifier = Modifier.height(24.dp))
            ButtonLogin(loginViewModel, shareViewModel, navigate)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Bạn chưa có tài khoản?",
                    fontSize = 14.sp
                )
                Text(
                    "Đăng ký ngay",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navigate.navigate("register")
                    })
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}