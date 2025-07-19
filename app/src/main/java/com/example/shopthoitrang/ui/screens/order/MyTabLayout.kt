package com.example.shopthoitrang.ui.screens.order
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.shopthoitrang.viewmodel.OrderViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel

@Composable
fun MyTabLayout(orderViewModel: OrderViewModel,navigate: NavHostController,shareViewModel: ShareViewModel) {
    var selectedTabIndex = orderViewModel.selectedTabIndex.collectAsState().value
    val tabs = listOf("Tất cả", "Đang giao", "Đã nhận")

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White, // Màu nền của TabRow
            contentColor = Color.Black,      // Màu của nội dung (text/icon mặc định)
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color.Black // Màu thanh gạch dưới
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { orderViewModel.setSelectedTabIndex(index) },
                    text = {
                        Text(
                            title,
                            color = Color.Black,
                            fontWeight = if(selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> OrderTab1(orderViewModel,navigate,shareViewModel)
            1 -> OrderTab2(orderViewModel,navigate,shareViewModel)
            2 -> OrderTab3(orderViewModel,navigate,shareViewModel)
        }
    }
}