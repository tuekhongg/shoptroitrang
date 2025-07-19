package com.example.shopthoitrang.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shopthoitrang.R
import com.example.shopthoitrang.viewmodel.SearchViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel
import com.example.shopthoitrang.viewmodel.factory.SearchFactory

@Composable
fun SearchScreen(shareViewModel: ShareViewModel, navigate: NavHostController) {
    val context = LocalContext.current
    val factory = SearchFactory(context)
    val searchViewModel: SearchViewModel = viewModel(factory = factory)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
            Spacer(modifier = Modifier.height(4.dp))
            SearchBar(navigate, searchViewModel)
            Spacer(modifier = Modifier.height(8.dp))
            SearchFilterBar(searchViewModel)
            Spacer(modifier = Modifier.height(8.dp))
            SearchResult(searchViewModel,navigate,shareViewModel)
    }
}
