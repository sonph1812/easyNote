package com.thanglong.easynote.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.compose.ui.platform.LocalView

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimaryDark,
    onPrimary = Color.Black,
    secondary = BlueLight,
    onSecondary = Color.Black,
    background = BackgroundDark,
    surface = SurfaceDark,
    onBackground = Color.White,
    onSurface = Color.White,
    primaryContainer = BlueDark,
    onPrimaryContainer = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = Color.White,
    secondary = BlueLight,
    onSecondary = Color.Black,
    background = PremiumWhite,
    surface = SurfaceLight,
    onBackground = TextDark,
    onSurface = TextDark,
    primaryContainer = BlueLight.copy(alpha = 0.3f),
    onPrimaryContainer = BlueDark
)

@Composable
fun Takenoteapp1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        window.statusBarColor = colorScheme.primary.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
