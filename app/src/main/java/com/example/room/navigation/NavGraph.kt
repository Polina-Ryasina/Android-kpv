package com.example.room.navigation

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.room.data.CharacterRepository
import com.example.room.db.AppDatabase
import com.example.room.data.UserRepository
import com.example.room.screens.addItem.AddItemScreen
import com.example.room.screens.addItem.AddItemViewModel
import com.example.room.screens.authorization.LoginScreen
import com.example.room.screens.authorization.LoginViewModel
import com.example.room.screens.contentList.ContentListScreen
import com.example.room.screens.contentList.ContentListViewModel
import com.example.room.screens.profile.ProfileScreen
import com.example.room.screens.profile.ProfileViewModel
import com.example.room.screens.register.RegisterScreen
import com.example.room.screens.register.RegisterViewModel
import com.example.room.utils.SessionManager
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.room.screens.delete.DeletedAccountScreen
import com.example.room.db.entity.UserEntity

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun NavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    val context = LocalContext.current.applicationContext as Application
    val db = AppDatabase.getDatabase(context)
    val userRepository = UserRepository(db.userDao())
    val characterRepository = CharacterRepository(db.characterDao())

    NavHost(
        navController = navController,
        startDestination = if (SessionManager.isLoggedIn) NavKeys.CONTENT_LIST else NavKeys.LOGIN,
    ) {

        composable(NavKeys.LOGIN) {
            val loginViewModel = remember { LoginViewModel(context, userRepository) }
            LoginScreen(
                viewModel = loginViewModel,
                onNavigateToRegister = { navController.navigate(NavKeys.REGISTER) },
                onLoginSuccess = {
                    navController.navigate(NavKeys.CONTENT_LIST) {
                        popUpTo(NavKeys.LOGIN) { inclusive = true }
                    }
                },
                onDeletedAccount = { userId ->
                    navController.navigate("${NavKeys.DELETED_ACCOUNT}/$userId") {
                        popUpTo(NavKeys.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(NavKeys.REGISTER) {
            val registerViewModel = RegisterViewModel(context, userRepository)

            RegisterScreen(
                viewModel = registerViewModel,
                onNavigateToLogin = { navController.popBackStack() },
                onRegistrationSuccess = {
                    navController.navigate(NavKeys.LOGIN) {
                        popUpTo(NavKeys.REGISTER) { inclusive = true }
                    }
                }
            )
        }

        composable(NavKeys.CONTENT_LIST) {
            val contentListViewModel = ContentListViewModel(context, characterRepository)

            ContentListScreen(
                viewModel = contentListViewModel,
                onAddItem = { navController.navigate(NavKeys.ADD_ITEM) },
                onProfileClick = { navController.navigate(NavKeys.PROFILE) }
            )
        }

        composable(NavKeys.ADD_ITEM) {
            val addItemViewModel = AddItemViewModel(context, characterRepository)

            AddItemScreen(
                viewModel = addItemViewModel,
                onAddSuccess = { navController.popBackStack() }
            )
        }

        composable(NavKeys.PROFILE) {
            val profileViewModel = ProfileViewModel(context, userRepository, characterRepository)

            ProfileScreen(
                profileViewModel,
                onLogout = { SessionManager.logout() }
            )
        }

        composable(
            route = "${NavKeys.DELETED_ACCOUNT}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable

            DeletedAccountScreen(
                userId = userId,
                repository = userRepository,
                onRestore = {
                    SessionManager.login(userId)
                    navController.navigate(NavKeys.CONTENT_LIST) {
                        popUpTo(NavKeys.LOGIN) { inclusive = true }
                    }
                },
                onDelete = {
                    navController.navigate(NavKeys.LOGIN) {
                        popUpTo(NavKeys.LOGIN) { inclusive = true }
                    }
                }
            )
        }
    }
}