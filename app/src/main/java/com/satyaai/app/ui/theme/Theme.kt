package com.satyaai.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SatyaDarkColors = darkColorScheme(
    primary = Color(0xFF7C4DFF),
    secondary = Color(0xFF03DAC5),
    tertiary = Color(0xFF4FC3F7),
    background = Color(0xFF0B0F1A),
    surface = Color(0xFF151B2A)
)

@Composable
fun SatyaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SatyaDarkColors,
        typography = Typography,
        content = content
    )
}
