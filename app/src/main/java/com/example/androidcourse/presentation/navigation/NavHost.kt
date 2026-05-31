package com.example.androidcourse.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidcourse.presentation.ui.GameDetailsScreen
import com.example.androidcourse.presentation.ui.OnboardingBottomSheet
import com.example.androidcourse.presentation.ui.SearchScreen
import com.example.androidcourse.presentation.viewmodel.DetailsViewModel
import com.example.androidcourse.presentation.viewmodel.SearchViewModel
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.crashlytics
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavHost(
    navController: NavHostController
) {

    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("app_prefs", android.content.Context.MODE_PRIVATE) }
    val isFirstLaunch = remember { mutableStateOf(prefs.getBoolean("onboarding_shown", true)) }

    LaunchedEffect(Unit) {
        if (isFirstLaunch.value) {
            Firebase.analytics.logEvent("show_onboarding") {}
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.SEARCH
    ) {
        composable(Routes.SEARCH) {
            LaunchedEffect(Unit) {
                Firebase.crashlytics.log("Screen opened: search")
            }

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

            LaunchedEffect(Unit) {
                Firebase.crashlytics.log("Screen opened: details, gameId=$gameId")
            }

            val viewModel: DetailsViewModel = koinViewModel(parameters = { parametersOf(gameId) })
            GameDetailsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }

    if (isFirstLaunch.value) {
        OnboardingBottomSheet(
            onDismiss = {
                Firebase.analytics.logEvent("close_onboarding") {}
                prefs.edit().putBoolean("onboarding_shown", false).apply()
                isFirstLaunch.value = false
            }
        )
    }
}
