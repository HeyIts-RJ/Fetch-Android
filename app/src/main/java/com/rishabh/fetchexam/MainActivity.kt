package com.rishabh.fetchexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rishabh.fetchexam.api.ApiService
import com.rishabh.fetchexam.ui.components.EmptyState
import com.rishabh.fetchexam.ui.theme.FetchExamTheme
import com.rishabh.fetchexam.viewmodel.MainActivityViewModel
import com.rishabh.fetchexam.viewmodel.MainViewModelFactory

/**
 * Starting point of our application
 * I implemented the MVVM architecture, where segregated the business logic from the UI
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // API Service class initalization
        val apiService = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)

        // ViewmodelFactory and ViewModel Initalization
        val viewModelFactory = MainViewModelFactory(apiService)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchExamTheme {
                // NavController initalization as I am using NavGraph but Compose implementation
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "mainScreen",
                ) {

                    composable("mainScreen") {
                        MainScreen(navController, viewModel)
                    }

                    composable("detailScreen/{listId}") { backStackEntry ->
                        val listId = backStackEntry.arguments?.getString("listId")?.toIntOrNull()
                        if (listId != null) {
                            DetailScreen(listId, viewModel)
                        } else {
                            EmptyState("Invalid List ID")
                        }
                    }
                }
            }
        }
    }
}
