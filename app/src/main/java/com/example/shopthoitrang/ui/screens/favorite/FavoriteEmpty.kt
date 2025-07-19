package com.example.shopthoitrang.ui.screens.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R

@Composable
fun FavoriteEmpty(navigate: NavHostController){
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = colorResource(R.color.white))
    ){
        Spacer(modifier = Modifier.height(16.dp))
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (icon,text) = createRefs()
            Icon(
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                modifier = Modifier.constrainAs(icon) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                    .clickable(
                        indication =null,
                        interactionSource = interactionSource
                    ){
                        navigate.popBackStack()
                    }
            )
            Text("YÊU THÍCH",
                modifier = Modifier.constrainAs(text) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.black)
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Chưa có sản phẩm yêu thích",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)
        }
    }
}