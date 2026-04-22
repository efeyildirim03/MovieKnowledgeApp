package com.example.movieknowledgeapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF6200EE),
    onPrimary = androidx.compose.ui.graphics.Color.White,
    // Define more colors if you like
)

@Composable
fun MovieTesterTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,  // You can swap to darkColorScheme if needed
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
