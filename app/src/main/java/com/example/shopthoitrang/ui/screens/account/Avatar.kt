package com.example.shopthoitrang.ui.screens.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.shopthoitrang.R
import com.example.shopthoitrang.viewmodel.AccountViewModel

@Composable
fun Avatar(accountViewModel: AccountViewModel) {
    val image = accountViewModel.avatar.collectAsState().value
    val selectedUri = accountViewModel.selectedUriImage.collectAsState().value
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        accountViewModel.setSelectedUri(uri)
        accountViewModel.saveUserFireStore()
    }
    ConstraintLayout {
        val (avatar, add) = createRefs()
        AsyncImage(
            model = selectedUri ?: image,
            contentDescription = "Avatar",
            error = painterResource(R.drawable.person),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier
                .size(90.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .constrainAs(avatar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            contentScale = ContentScale.Crop
        )
        Canvas(
            modifier = Modifier
                .size(25.dp)
                .background(
                    color = Color.Black,
                    CircleShape
                )
                .clip(CircleShape)
                .constrainAs(add) {
                    end.linkTo(avatar.end)
                    bottom.linkTo(avatar.bottom)
                }
                .clickable {
                    launcher.launch("image/*")
                }) {
            drawLine(
                color = Color.White,
                start = Offset(size.width / 2, 10f),
                end = Offset(size.width / 2, size.height - 10f),
                strokeWidth = 8f,
            )
            drawLine(
                color = Color.White,
                start = Offset(10f, size.height / 2),
                end = Offset(size.width - 10f, size.height / 2),
                strokeWidth = 8f,
            )
        }
    }
}
