package com.retr0.projectretr0.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val RetroColorScheme = darkColorScheme(
    background = RetroBg,
    surface = RetroSurface,
    surfaceVariant = RetroElevated,
    primary = RetroAccent,
    onPrimary = Color.White,
    onBackground = RetroText,
    onSurface = RetroText,
    error = RetroDanger,
    outline = RetroBorder,
)

@Composable
fun ProjectRETR0InstallerTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = RetroColorScheme,
        typography = Typography,
        content = content,
    )
}
