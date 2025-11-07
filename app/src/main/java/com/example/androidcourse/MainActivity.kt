package com.example.androidcourse

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.navigation.compose.rememberNavController
import com.example.androidcourse.navigation.BottomNavigationBar
import com.example.androidcourse.navigation.NavGraph
import com.example.androidcourse.ui.theme.MyAppTheme


class MainActivity : ComponentActivity() {

    private val notificationPermissionHandler =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            val messageRes = if (granted) {
                R.string.notification_permission_granted
            } else {
                R.string.notification_permission_denied
            }
            Toast.makeText(this, getString(messageRes), Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        verifyNotificationAccess()
        setContent {
            MyAppTheme {
                Screen()
            }
        }
    }

    private fun verifyNotificationAccess() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        val hasPermission =
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        if (!hasPermission) {
            notificationPermissionHandler.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

@Composable
fun Screen() {
    val navController = rememberNavController()
    var bottomBarHeight by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController,
                modifier = Modifier.onSizeChanged { size: IntSize -> bottomBarHeight = size.height }
            )
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            innerPadding = innerPadding,
            bottomBarHeight = bottomBarHeight
        )
    }
}

