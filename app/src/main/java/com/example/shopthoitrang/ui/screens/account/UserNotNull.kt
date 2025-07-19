package com.example.shopthoitrang.ui.screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.viewmodel.AccountViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun UserNotNull(
    navigate: NavHostController,
    shareViewModel: ShareViewModel,
    accountViewModel: AccountViewModel
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.navigation)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
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
                Text("ACCOUNT",
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
            Spacer(modifier = Modifier.height(48.dp))
            Avatar(accountViewModel)
            Spacer(modifier = Modifier.height(16.dp))
            NameAndEmail(accountViewModel)
            Spacer(modifier = Modifier.height(16.dp))
            OptionMenu(shareViewModel, accountViewModel)
        }
    }
    val show = accountViewModel.showChangePassword.collectAsState().value
    val showinfo = accountViewModel.showDialogUserInfo.collectAsState().value
    DialogChangePassword(show, accountViewModel)
    DialogInfoUser(showinfo, accountViewModel)
}
@Composable
private fun NameAndEmail(accountViewModel: AccountViewModel) {
    val name = accountViewModel.name.collectAsState().value
    val email = accountViewModel.email.collectAsState().value
    Text(
        name, color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    Text(
        email,
        color = Color.DarkGray
    )
}

