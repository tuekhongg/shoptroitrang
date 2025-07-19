package com.example.shopthoitrang.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopthoitrang.viewmodel.AccountViewModel
import com.example.shopthoitrang.viewmodel.ShareViewModel

class AccountFactory(val shareViewModel: ShareViewModel, val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountViewModel(shareViewModel,context) as T
    }
}