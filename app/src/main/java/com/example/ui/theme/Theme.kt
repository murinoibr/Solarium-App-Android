package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SolariumPrimaryDark,
    onPrimary = Color(0xFF4E1D00),
    primaryContainer = SolariumPrimaryContainerDark,
    onPrimaryContainer = SolariumSunOrangeContainer,
    secondary = SolariumSecondaryDark,
    onSecondary = Color(0xFF5A1E07),
    secondaryContainer = SolariumSecondaryContainerDark,
    onSecondaryContainer = SolariumTerracottaContainer,
    tertiary = SolariumTertiaryDark,
    onTertiary = Color(0xFF402800),
    tertiaryContainer = SolariumTertiaryContainerDark,
    onTertiaryContainer = SolariumSoftYellowContainer,
    background = SolariumBackgroundDark,
    onBackground = SolariumOnBackgroundDark,
    surface = SolariumSurfaceDark,
    onSurface = SolariumOnSurfaceDark,
    surfaceVariant = SolariumSurfaceVariantDark,
    onSurfaceVariant = SolariumOnSurfaceVariantDark,
    outline = SolariumOutlineDark,
    outlineVariant = SolariumOutlineVariantDark
)

private val LightColorScheme = lightColorScheme(
    primary = SolariumSunOrange,
    onPrimary = Color.White,
    primaryContainer = SolariumSunOrangeContainer,
    onPrimaryContainer = OnSolariumSunOrangeContainer,
    secondary = SolariumTerracotta,
    onSecondary = Color.White,
    secondaryContainer = SolariumTerracottaContainer,
    onSecondaryContainer = OnSolariumTerracottaContainer,
    tertiary = SolariumSoftYellow,
    onTertiary = Color.White,
    tertiaryContainer = SolariumSoftYellowContainer,
    onTertiaryContainer = OnSolariumSoftYellowContainer,
    background = SolariumCreamBackground,
    onBackground = SolariumTextPrimary,
    surface = SolariumCreamSurface,
    onSurface = SolariumTextPrimary,
    surfaceVariant = SolariumCreamSurfaceVariant,
    onSurfaceVariant = SolariumTextSecondary,
    outline = SolariumOutline,
    outlineVariant = SolariumOutlineVariant
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep warm signature Solarium palette
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val customColors = if (darkTheme) DarkSolariumCustomColors else LightSolariumCustomColors

    CompositionLocalProvider(
        LocalSolariumCustomColors provides customColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

@Composable
fun SolariumAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MyApplicationTheme(darkTheme = darkTheme, dynamicColor = false, content = content)
}
