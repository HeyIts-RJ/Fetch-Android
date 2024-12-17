package com.rishabh.fetchexam.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rishabh.fetchexam.models.Item


@Composable
fun GroupedItemList(groups: Map<Int, List<Item>>, onListIdClick: (Int) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        groups.keys.forEach { listId ->
            item {
                Text(
                    textAlign = TextAlign.Start,
                    text = "List ID: $listId",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            onListIdClick(listId)
                        },
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}