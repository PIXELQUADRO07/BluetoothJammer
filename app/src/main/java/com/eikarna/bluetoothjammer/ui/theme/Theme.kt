package com.eikarna.bluetoothjammer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CyberColorScheme = darkColorScheme(
    primary = CyberPrimary,
    secondary = CyberSecondary,
    tertiary = CyberTertiary,
    background = CyberBackground,
    surface = CyberSurface,
    onPrimary = CyberOnPrimary,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = CyberText,
    onSurface = CyberText,
    error = CyberError
)

@Composable
fun CyberpunkTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CyberColorScheme,
        typography = Typography,
        content = content
    )
}
