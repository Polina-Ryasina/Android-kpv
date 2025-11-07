package com.example.androidcourse.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.androidcourse.R

data class BottomNavItem(
    val endpoint: String,
    val icon: @Composable () -> Unit,
    val label: String
)

@Composable
fun BottomNavigationBar(navController: NavController, modifier: Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem(
            endpoint = NavKeys.MAIN,
            icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
            label = stringResource(R.string.blank)
        ),
        BottomNavItem(
            endpoint = NavKeys.EDIT,
            icon = { Icon(Icons.Default.Edit, contentDescription = null) },
            label = stringResource(R.string.blank)
        ),
        BottomNavItem(
            endpoint = NavKeys.MESSAGES,
            icon = { Icon(Icons.Default.Email, contentDescription = null) },
            label = stringResource(R.string.blank)
        )
    )

    val colors = MaterialTheme.colorScheme

    NavigationBar(
        containerColor = colors.background,
        contentColor = colors.onBackground
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.endpoint
            NavigationBarItem(
                icon = item.icon,
                selected = selected,
                onClick = {
                    navController.navigate(item.endpoint) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colors.primary,
                    unselectedIconColor = colors.secondary,
                    selectedTextColor = colors.primary,
                    unselectedTextColor = colors.secondary
                )
            )
        }
    }
}
