package com.example.androidcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.androidcourse.nav.NavGraph
import com.example.androidcourse.ui.theme.CustomTheme
import com.example.androidcourse.ui.theme.EnumOfThemes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currTheme by rememberSaveable { mutableStateOf(EnumOfThemes.AQUA) }
            CustomTheme(theme = currTheme) {
                NavGraph(onThemeChange = { currTheme = it })
            }
        }
    }
}

