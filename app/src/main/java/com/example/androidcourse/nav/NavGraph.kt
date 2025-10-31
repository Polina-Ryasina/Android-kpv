package com.example.androidcourse.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidcourse.ui.screens.auth.AuthorizationScreen
import com.example.androidcourse.ui.screens.main.MainScreen
import com.example.androidcourse.ui.screens.newNote.NewNoteScreen
import com.example.androidcourse.ui.theme.EnumOfThemes


@Composable
fun NavGraph(onThemeChange: (EnumOfThemes) -> Unit) {
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
                onThemeChange = onThemeChange,
                navController = navController,
                email = email,
            )
        }

        composable(NavKeys.NEWNOTE) {
            NewNoteScreen(navController = navController)
        }

        composable(NavKeys.UPDATENOTE) {
            val id = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Int>(NavKeys.ID)
            NewNoteScreen(navController = navController, id = id)
        }



    }

}