package com.example.shopthoitrang.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R

@Composable
fun HeaderLogin(navigate: NavHostController){
    val interactionSource = remember {
        MutableInteractionSource()
    }
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (back,image) = createRefs()
        Icon(
            painter = painterResource(R.drawable.back),
            contentDescription = null,
            modifier = Modifier.constrainAs(back) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ){
                    navigate.popBackStack()
                }
        )
        Image(
            painter = painterResource(R.drawable.login),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(top = 16.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}