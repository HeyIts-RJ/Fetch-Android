package com.rishabh.fetchexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rishabh.fetchexam.api.ApiService
import com.rishabh.fetchexam.repository.ItemRepository

class MainViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(ItemRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}