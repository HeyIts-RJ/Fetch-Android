package com.rishabh.fetchexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rishabh.fetchexam.models.Item
import com.rishabh.fetchexam.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: ItemRepository) : ViewModel() {

    private val _groupedData = MutableStateFlow<Map<Int, List<Item>>?>(null)
    val groupedData: StateFlow<Map<Int, List<Item>>?> get() = _groupedData

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                val data = repository.getItems()
                if (data != null) {
                    _groupedData.value = data
                } else {
                    _errorMessage.value = "Failed to load data"
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}