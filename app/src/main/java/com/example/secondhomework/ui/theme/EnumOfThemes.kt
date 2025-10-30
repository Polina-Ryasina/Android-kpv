package com.example.secondhomework.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class EnumOfThemes(val displayName: String) {
    AQUA("AquaSerenity"),
    TWILIGHT("TwilightViolet"),
    AUTUMN("AutumnCocoa")
}
object ThemeManager {
    var currentTheme by mutableStateOf(EnumOfThemes.AQUA)
}


