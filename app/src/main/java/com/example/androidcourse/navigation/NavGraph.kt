package com.example.androidcourse.navigation

import FirstScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidcourse.ui.screens.secondscreen.SecondScreen
import com.example.androidcourse.ui.screens.thirdscreen.ThirdScreen
import com.example.androidcourse.ui.theme.MyAppTheme

@Composable
fun NavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    bottomBarHeight: Int
) {
    NavHost(
        navController = navController,
        startDestination = NavKeys.MAIN
    ) {
        composable(NavKeys.MAIN) {
            MyAppTheme {
                FirstScreen(
                    navController = navController,
                    innerPadding = innerPadding,
                    bottomBarHeight = bottomBarHeight
                )
            }
        }
        composable(NavKeys.EDIT) {
            MyAppTheme {
                SecondScreen(navController = navController, innerPadding = innerPadding)
            }
        }
        composable(NavKeys.MESSAGES) {
            MyAppTheme {
                ThirdScreen(navController = navController, innerPadding = innerPadding)
            }
        }
    }
}