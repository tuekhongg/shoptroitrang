package com.example.shopthoitrang.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopthoitrang.viewmodel.FavoriteViewModel

class FavoriteFactory(val context : Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(context) as T
    }
}