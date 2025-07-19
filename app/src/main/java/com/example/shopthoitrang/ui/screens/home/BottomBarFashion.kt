package com.example.shopthoitrang.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.model.NavItem
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun BottomBarFashion(navController: NavHostController, shareViewModel: ShareViewModel) {
    val listItem = listOf(
        NavItem(
            title = "Trang chủ",
            icon = painterResource(R.drawable.home_select),
            route = "home"
        ),
        NavItem(
            title = "Yêu thích",
            icon = painterResource(R.drawable.favorite_unselect),
            route = "favorite"
        ),
        NavItem(
            title = "Đơn hàng",
            icon = painterResource(R.drawable.order),
            route = "order"
        ),
        NavItem(
            title = "Tài khoản",
            icon = painterResource(R.drawable.account_unselect),
            route = "account"
        )
    )

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.navigation))
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listItem.forEachIndexed { index, item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(
                        color = colorResource(R.color.navigation),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
            ) {
                Icon(
                    painter = item.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(32.dp)
                        .height(32.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = item.title,
                    color = if (index == 0) colorResource(R.color.black) else colorResource(
                        R.color.textnavigationunselect
                    ),
                    fontSize = 12.sp,
                    maxLines = 1,
                    style = TextStyle(
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.Both
                        ),
                        fontWeight = if (index == 0) FontWeight.Bold else FontWeight.Normal
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }

}
