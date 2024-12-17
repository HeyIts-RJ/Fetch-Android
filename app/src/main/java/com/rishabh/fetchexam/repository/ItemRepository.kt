package com.rishabh.fetchexam.repository

import android.util.Log
import com.rishabh.fetchexam.api.ApiService
import com.rishabh.fetchexam.models.Item
import com.rishabh.fetchexam.models.Items
import retrofit2.Response

class ItemRepository(private val apiService: ApiService) {

    suspend fun getItems(): Map<Int, List<Item>>? {
        val response: Response<Items> = apiService.getHiringJson()

        try {
            if (response.isSuccessful) {
                val items = response.body() ?: return null

                // Filter out items with blank or null names
                val filteredItems = filterItems(items)

                // Sort by listId first, then by name
                val sortedItems = filteredItems.sortedWith(compareBy({ it.listId }, { it.name }))

                // Group by listId
                return sortedItems.groupBy { it.listId }
            } else {
                // Handle API error (e.g., log error or return null)
                return null
            }
        } catch (e: Exception) {
            Log.e("Item Repository", "getItems: ${e.printStackTrace()}")
            return null
        }
    }

    fun filterItems(items: List<Item>): List<Item> {
        return items.filter { it.name != null && it.name.isNotBlank() }
    }
}