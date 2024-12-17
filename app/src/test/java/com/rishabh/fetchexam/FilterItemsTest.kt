package com.rishabh.fetchexam

import com.rishabh.fetchexam.api.ApiService
import com.rishabh.fetchexam.models.Item
import com.rishabh.fetchexam.repository.ItemRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FilterItemsTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var repository: ItemRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = ItemRepository(apiService)
    }

    @Test
    fun `filterItems removes items with null or blank names`() {
        val sampleItems = listOf(
            Item(id = 1, listId = 1, name = "Item A"),
            Item(id = 2, listId = 1, name = ""),
            Item(id = 3, listId = 2, name = null),
            Item(id = 4, listId = 2, name = "Item B")
        )

        val filteredItems = repository.filterItems(sampleItems)

        assertEquals(2, filteredItems.size)
        assertTrue(filteredItems.all { it.name != null && it.name!!.isNotBlank() })
    }
}