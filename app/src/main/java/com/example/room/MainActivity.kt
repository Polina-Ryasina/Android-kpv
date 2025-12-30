package com.example.room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.room.data.UserRepository
import com.example.room.db.AppDatabase
import com.example.room.navigation.NavGraph
import com.example.room.ui.theme.RoomTheme
import com.example.room.utils.DeleteNotRestoredAccounts
import com.example.room.utils.SessionManager
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManager.init(applicationContext)
        setupDeleteWorker()

        enableEdgeToEdge()
        setContent {
            RoomTheme {
                val navController = rememberNavController()

                NavGraph(
                    navController = navController,
                    innerPadding = PaddingValues()
                )
            }
        }
    }

    private fun setupDeleteWorker() {
        val workRequest = PeriodicWorkRequestBuilder<DeleteNotRestoredAccounts>(1, TimeUnit.DAYS).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "delete_marked_users",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}