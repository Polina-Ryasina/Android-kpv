package com.example.androidcourse.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


data class ColorThemes(
    val borderColor: Color,
    val backgroundColor: Color,
    val errorMessageColor: Color,
    val textColor: Color,
    val buttonColor: Color,
    val textFieldColor: Color
)

val AquaSerenity = ColorThemes(
    borderColor = Color(0xFF008F8C),
    backgroundColor = Color(0xFFD8FFDB),
    errorMessageColor = Color(0xFF023535),
    textColor = Color(0xFF01595B),
    buttonColor = Color(0xFF0FC2C0),
    textFieldColor = Color(0xFFC7FFED)
)


val TwilightViolet = ColorThemes(
    borderColor = Color(0xFF5549A6),
    backgroundColor = Color(0xFFA993BF),
    errorMessageColor = Color(0xFFF249F2),
    textColor = Color(0xFF212340),
    buttonColor = Color(0xFF8A00F2),
    textFieldColor = Color(0xFF483E8C)
)

val AutumnCocoa = ColorThemes(
    borderColor = Color(0xFF73412F),
    backgroundColor = Color(0xFFD9BCA3),
    errorMessageColor = Color(0xFFD98555),
    textColor = Color(0xFF0D0D0D),
    buttonColor = Color(0xFFF2AA6B),
    textFieldColor = Color(0xFFDCAD94)
)

val LocalAppColorScheme = staticCompositionLocalOf {
    ColorThemes(
        borderColor = AquaSerenity.borderColor,
        backgroundColor = AquaSerenity.backgroundColor,
        errorMessageColor = AquaSerenity.errorMessageColor,
        textColor = AquaSerenity.textColor,
        buttonColor = AquaSerenity.buttonColor,
        textFieldColor = AquaSerenity.textFieldColor,
    )
}

@Composable
fun CustomTheme(
    theme: EnumOfThemes,
    content: @Composable () -> Unit
) {
    val colors = when (theme) {
        EnumOfThemes.AQUA -> AquaSerenity
        EnumOfThemes.TWILIGHT -> TwilightViolet
        EnumOfThemes.AUTUMN -> AutumnCocoa
    }

    CompositionLocalProvider(LocalAppColorScheme provides colors) {
        content()
    }
}

