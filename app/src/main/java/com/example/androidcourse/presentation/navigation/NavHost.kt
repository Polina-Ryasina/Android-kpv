package com.example.androidcourse.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidcourse.presentation.ui.GameDetailsScreen
import com.example.androidcourse.presentation.ui.SearchScreen
import com.example.androidcourse.presentation.viewmodel.DetailsViewModel
import com.example.androidcourse.presentation.viewmodel.SearchViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    detailsViewModel: DetailsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SEARCH
    ) {
        composable(Routes.SEARCH) {
            SearchScreen(
                viewModel = searchViewModel,
                onNavigateToDetails = { game ->
                    detailsViewModel.setGame(game)
                    navController.navigate(Routes.DETAILS)
                }
            )
        }
        composable(Routes.DETAILS) {
            GameDetailsScreen(
                viewModel = detailsViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
