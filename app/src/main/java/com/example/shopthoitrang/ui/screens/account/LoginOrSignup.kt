package com.example.shopthoitrang.ui.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R

@Composable
fun LoginOrSignup(navigate: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = colorResource(R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val interactionSource = remember {
            MutableInteractionSource()
        }
        Spacer(modifier = Modifier.height(16.dp))
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (icon, text) = createRefs()
            Icon(
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                modifier = Modifier.constrainAs(icon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, margin = 16.dp)
                }
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ){
                        navigate.navigate("home")
                    }
            )
            Text(
                text = "Thông tin tài khoản",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(250.dp)
                .padding(top = 16.dp, bottom = 16.dp)
        )
        Text(
            "Bạn chưa đăng nhập? Vui lòng",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {
                navigate.navigate("login")
            },
            modifier = Modifier.width(160.dp)
                .height(45.dp)
        ) {
            Text("Đăng nhập",
                color = colorResource(R.color.black),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text("hoặc")
        OutlinedButton(
            onClick = {
                navigate.navigate("register")
            },
            modifier = Modifier.width(160.dp)
                .height(45.dp)
        ) {
            Text("Đăng ký",
                color = colorResource(R.color.black),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

