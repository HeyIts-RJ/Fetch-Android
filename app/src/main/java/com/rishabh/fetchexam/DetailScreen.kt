package com.rishabh.fetchexam

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rishabh.fetchexam.ui.components.EmptyState
import com.rishabh.fetchexam.viewmodel.MainActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(listId: Int, viewModel: MainActivityViewModel) {
    val groupedData by viewModel.groupedData.collectAsState()

    // Get the list of items for the selected listId
    //    We are doing lexicographical sorting rather than numerical sorting, as there is no sorting method is mentioned
    val itemsForListId = groupedData?.get(listId)?.sortedBy { it.name }

    // Extract numeric value from the name field for sorting (Numerical Sorting logic)
//    val itemsForListId = groupedData?.get(listId)?.sortedWith(
//        compareBy { item ->
//            item.name.toIntOrNull() ?: Int.MAX_VALUE
//        }
//    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("List ID: $listId - Items") },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (itemsForListId.isNullOrEmpty()) {
                // Display an empty state if no items are found
                EmptyState("No items found!")
            } else {
                // Display only the list of items
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(itemsForListId) { item ->
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}