package com.plznoanr.lol.core.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Black,
    primaryContainer = White,
    onPrimary = SkyBlue,
    onPrimaryContainer = White,
    secondary = White,
    secondaryContainer = Purple40,
    inversePrimary = PurpleGrey40,
    onError = Red,
    surface = LightGray,
    onSurface = DarkGray,
    tertiary = LightGray,
    onTertiary = Black,
    onTertiaryContainer = DarkGray,
)

private val LightColorScheme = lightColorScheme(
    primary = White,
    primaryContainer = White,
    inversePrimary = SkyBlue,
    onPrimary = Black,
    onPrimaryContainer = Graphite,
    secondary = Black,
    secondaryContainer = Purple40,
    onError = Red,
    surface = LightGray,
    onSurface = Black,
    tertiary = LightGray,
    onTertiary = Black,
    onTertiaryContainer = DarkGray,
)

val LocalDarkTheme = compositionLocalOf { false }

// Custom Color
val ColorScheme.navigationSelect
    @Composable get() = if (LocalDarkTheme.current) Graphite else SkyBlue
val ColorScheme.navigationUnSelect
    @Composable get() = DarkGray
val ColorScheme.navigationIndicator
    @Composable get() = LightGray

@Composable
fun LolUserSearchComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalDarkTheme provides darkTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }

}