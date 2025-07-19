package com.example.shopthoitrang.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(navigate: NavHostController) {
    val viewModel: RegisterViewModel = viewModel()
    val context = LocalContext.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
   Column(
       modifier = Modifier.fillMaxSize()
           .background(color = colorResource(R.color.white))
   ) {
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
                       navigate.popBackStack()
                   }
           )
           Text(
               text = "Đăng ký tài khoản",
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
       LazyColumn(
           modifier = Modifier
               .fillMaxSize()
               .padding(16.dp)
               .background(color = colorResource(R.color.white)),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           item {
               FieldRegister(viewModel)
               Button(
                   onClick = {
                       val notification = viewModel.checkInfo()
                       if (notification == "")
                           viewModel.checkUsername { it ->
                               if (it) Toast.makeText(
                                   context,
                                   "Tên đăng nhập đã tồn tại",
                                   Toast.LENGTH_SHORT
                               ).show()
                               else {
                                   viewModel.registerUserWithEmailVerification(
                                       onError = {
                                               mg->
                                           Toast.makeText(context,mg, Toast.LENGTH_SHORT).show()
                                       },
                                       onSuccess = {
                                           viewModel.setShowDialogSendEmail(true)
                                       }
                                   )
                               }
                           }
                       else Toast.makeText(context, notification, Toast.LENGTH_SHORT).show()
                   },
                   colors = ButtonDefaults.buttonColors(
                       containerColor = Color.Black,
                       contentColor = Color.White
                   ),
                   modifier = Modifier
                       .padding(top = 24.dp)
                       .height(60.dp)
                       .width(200.dp)
               ) {
                   Text(
                       "Đăng ký",
                       fontSize = 20.sp
                   )
               }
               Column(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(top = 12.dp),
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {
                   Text("Bạn đã có tài khoản? ")
                   Text(
                       "Đăng nhập ngay!",
                       textDecoration = TextDecoration.Underline,
                       modifier = Modifier.clickable {
                           navigate.navigate("login")
                       }
                   )
               }
               val show = viewModel.showDialogSendEmail.collectAsState().value
               val showFail = viewModel.showFailDialog.collectAsState().value
               val showSuccess = viewModel.showSuccessDialog.collectAsState().value
               DialogRegister(show, viewModel)
               FailDialogRegister(showFail,viewModel)
               SuccessDialogRegister(showSuccess,viewModel)
               Spacer(modifier = Modifier.height(32.dp))
           }
       }
   }
}