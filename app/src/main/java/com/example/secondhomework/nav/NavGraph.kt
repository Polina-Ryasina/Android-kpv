package com.example.secondhomework.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.secondhomework.ui.screens.auth.AuthorizationScreen
import com.example.secondhomework.ui.screens.main.MainScreen
import com.example.secondhomework.ui.screens.newNote.NewNoteScreen


@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavKeys.LOGIN
    ) {
        composable(NavKeys.LOGIN) {
            AuthorizationScreen(navController = navController)
        }

        composable(NavKeys.MAIN) { backStackEntry ->
            val email = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>(NavKeys.EMAIL) ?: ""
            MainScreen(
                navController = navController,
                email = email,
            )
        }

        composable(NavKeys.NEWNOTE) {
            NewNoteScreen(navController = navController)
        }

    }

}