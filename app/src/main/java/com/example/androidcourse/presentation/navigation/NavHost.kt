package com.example.androidcourse.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidcourse.presentation.ui.GameDetailsScreen
import com.example.androidcourse.presentation.ui.SearchScreen
import com.example.androidcourse.presentation.viewmodel.DetailsViewModel
import com.example.androidcourse.presentation.viewmodel.SearchViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SEARCH
    ) {
        composable(Routes.SEARCH) {
            val viewModel: SearchViewModel = koinViewModel()
            SearchScreen(
                viewModel = viewModel,
                onNavigateToDetails = { game ->
                    navController.navigate(Routes.details(game.id))
                }
            )
        }

        composable(
            route = Routes.DETAILS,
            arguments = listOf(navArgument("gameId") { type = NavType.IntType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId") ?: return@composable
            val viewModel: DetailsViewModel = koinViewModel(parameters = { parametersOf(gameId) })
            GameDetailsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
