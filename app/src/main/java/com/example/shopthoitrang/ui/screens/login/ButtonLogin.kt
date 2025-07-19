package com.example.shopthoitrang.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.viewmodel.LoginViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun ButtonLogin(
    loginViewModel: LoginViewModel,
    shareViewModel: ShareViewModel,
    navigate: NavHostController
) {
    val isLoading = loginViewModel.isLoading.collectAsState().value
    val context = LocalContext.current
    val keyBoard = LocalSoftwareKeyboardController.current
    Button(
        onClick = {
            keyBoard?.hide()
            val checkinfo = loginViewModel.checkInfo()
            if (checkinfo == "")
                loginViewModel.loginWithUsernameAndPassword(
                    onSuccess = { user ->
                        shareViewModel.setUser(user)
                        navigate.navigate("home")
                    },
                    onError = {
                        Toast.makeText(
                            context,
                            "Tên đăng nhập hoặc mật khẩu không chính xác",
                            Toast.LENGTH_SHORT
                        ).show()
                        loginViewModel.setIsloading(false)
                    }
                )
            else Toast.makeText(context, checkinfo, Toast.LENGTH_SHORT).show()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.black),
            contentColor = colorResource(R.color.white)
        ),
        modifier = Modifier
            .height(60.dp)
            .width(250.dp),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = colorResource(R.color.black),
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
            )
        } else {
            Text("Đăng nhập", fontSize = 20.sp)
        }
    }
}