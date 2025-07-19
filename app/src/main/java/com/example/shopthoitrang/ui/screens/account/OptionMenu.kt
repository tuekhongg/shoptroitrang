package com.example.shopthoitrang.ui.screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shopthoitrang.R
import com.example.shopthoitrang.viewmodel.AccountViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun OptionMenu(
    shareViewModel: ShareViewModel,
    accountViewModel: AccountViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(color = colorResource(R.color.navigation))
    ) {
        ItemOptionMenu(
            icon = painterResource(R.drawable.settings),
            title = "Cài đặt"
        ) {

        }
        Spacer(modifier = Modifier.height(16.dp))
        ItemOptionMenu(
            icon = painterResource(R.drawable.notification),
            title = "Thông báo"
        ) {

        }
        Spacer(modifier = Modifier.height(16.dp))
        ItemOptionMenu(
            icon = painterResource(R.drawable.info),
            title = "Thông tin tài khoản"
        ) {
            accountViewModel.setShowDialogUserInfo(true)
        }
        Spacer(modifier = Modifier.height(16.dp))
        ItemOptionMenu(
            icon = painterResource(R.drawable.changepassword),
            title = "Đổi mật khẩu"
        ) {
            accountViewModel.setShowChangePassword(true)
        }
        Spacer(modifier = Modifier.height(16.dp))
        ItemOptionMenu(
            icon = painterResource(R.drawable.logout),
            title = "Đăng xuất"
        ) {
            shareViewModel.setUser(null)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}