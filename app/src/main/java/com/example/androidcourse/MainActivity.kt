package com.example.androidcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.androidcourse.screens.AppRoot
import com.example.androidcourse.screens.*


class MainActivity : ComponentActivity() {
    private val viewModel: CoroutineViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppRoot(viewModel)
        }
    }
}