package com.rishabh.fetchexam

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.rishabh.fetchexam.ui.components.EmptyState
import com.rishabh.fetchexam.ui.components.GroupedItemList
import com.rishabh.fetchexam.viewmodel.MainActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainActivityViewModel) {

    val groupedData by viewModel.groupedData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("List IDs") },
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
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        // Progress bar when API is being call
                        CircularProgressIndicator()
                    }
                }

                errorMessage != null -> {
                    EmptyState("Unknown error!")
                }

                groupedData != null -> {
                    GroupedItemList(groupedData!!) { listId ->
                        // Navigate to Detail Screen with listId as argument
                        navController.navigate("detailScreen/$listId")
                    }
                }

                else -> {
                    EmptyState("Server issue")
                }
            }
        }
    }
}