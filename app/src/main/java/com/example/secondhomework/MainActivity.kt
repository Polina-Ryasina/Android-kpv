package com.example.secondhomework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.secondhomework.nav.NavGraph
import com.example.secondhomework.ui.theme.CustomTheme
import com.example.secondhomework.ui.theme.ThemeManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomTheme(theme = ThemeManager.currentTheme) {
                NavGraph()
            }
        }
    }
}

