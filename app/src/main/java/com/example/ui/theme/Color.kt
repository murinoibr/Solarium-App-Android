package com.example.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// =========================================================================
// 'Solarium' Theme Palette: Warm, Sunny, and Welcoming Tones
//
// Inspired by sun-drenched verandas, golden-hour radiance, soft sunbeams,
// sun-bleached cream linen, warm terracotta clay, and gentle morning light.
// =========================================================================

// --- 1. Laranjas de Sol (Solar Orange & Radiant Amber) ---
val SolariumSunOrange = Color(0xFFDC5B16)             // Vibrant solar orange (AA/AAA contrast)
val SolariumSunOrangeLight = Color(0xFFF97316)        // Radiant sun orange accent
val SolariumSunOrangeDark = Color(0xFFB54508)         // Deep sunlit burnt orange
val SolariumSunOrangeContainer = Color(0xFFFFE6D6)    // Warm soft peach/orange container
val OnSolariumSunOrangeContainer = Color(0xFF3F1300)

// --- 2. Amarelos Suaves & Dourados (Soft Sunbeams & Golden Hour) ---
val SolariumSoftYellow = Color(0xFFD68A00)            // Warm golden amber
val SolariumSoftYellowLight = Color(0xFFFBBF24)       // Gentle sunbeam yellow
val SolariumSoftYellowContainer = Color(0xFFFFF0CB)   // Soft warm sunbeam cream-yellow
val OnSolariumSoftYellowContainer = Color(0xFF422800)
val SolariumSunbeam = Color(0xFFFFF7E3)               // Delicate sunlit tint
val SolariumSunGlow = Color(0xFFF59E0B)               // Radiant morning sun glow

// --- 3. Cremes & Marfins Acolhedores (Warm Creams, Ivory & Sunlit Linens) ---
val SolariumCreamBackground = Color(0xFFFAF7F0)       // Sunny canvas background
val SolariumCreamSurface = Color(0xFFFFFDF9)          // Warm pure ivory surface
val SolariumCreamSurfaceVariant = Color(0xFFF3ECE1)   // Warm sun-warmed cream container
val SolariumCreamCard = Color(0xFFFFFBF5)             // Warm elevated card surface
val SolariumWarmLinen = Color(0xFFEDE4D7)             // Soft warm linen divider
val SolariumLinenBorder = Color(0xFFE5D9CA)           // Muted warm border

// --- 4. Tons de Terracota (Warm Sunbaked Terracotta) ---
val SolariumTerracotta = Color(0xFFB84C26)            // Warm sunbaked clay
val SolariumTerracottaLight = Color(0xFFD66940)       // Sunburst terracotta
val SolariumTerracottaContainer = Color(0xFFFFDBD0)   // Soft terracotta cream container
val OnSolariumTerracottaContainer = Color(0xFF3C1204)

// --- 5. Neutros, Textos e Contornos ---
val SolariumTextPrimary = Color(0xFF231B15)           // Rich warm espresso charcoal
val SolariumTextSecondary = Color(0xFF6E5E52)         // Soft warm mocha
val SolariumOutline = Color(0xFF8F7E70)               // Warm neutral outline
val SolariumOutlineVariant = Color(0xFFDDD2C4)        // Delicate warm outline

// --- 6. Dark Theme (Sunset Solarium & Twilight Radiance) ---
val SolariumPrimaryDark = Color(0xFFFFB68C)
val SolariumPrimaryContainerDark = Color(0xFF702800)
val SolariumSecondaryDark = Color(0xFFFFB59E)
val SolariumSecondaryContainerDark = Color(0xFF6E2810)
val SolariumTertiaryDark = Color(0xFFFFDF88)
val SolariumTertiaryContainerDark = Color(0xFF563900)
val SolariumBackgroundDark = Color(0xFF191410)
val SolariumSurfaceDark = Color(0xFF231C17)
val SolariumSurfaceVariantDark = Color(0xFF342921)
val SolariumOnBackgroundDark = Color(0xFFEFE4D8)
val SolariumOnSurfaceDark = Color(0xFFEFE4D8)
val SolariumOnSurfaceVariantDark = Color(0xFFD5C3B4)
val SolariumOutlineDark = Color(0xFF9E8D7F)
val SolariumOutlineVariantDark = Color(0xFF524438)

// =========================================================================
// Custom Solarium Theme Model
// =========================================================================

@Immutable
data class SolariumCustomColors(
    val sunOrange: Color,
    val sunOrangeLight: Color,
    val sunOrangeContainer: Color,
    val onSunOrangeContainer: Color,
    val softYellow: Color,
    val softYellowContainer: Color,
    val onSoftYellowContainer: Color,
    val sunbeam: Color,
    val sunGlow: Color,
    val warmTerracotta: Color,
    val warmTerracottaContainer: Color,
    val onWarmTerracottaContainer: Color,
    val creamBackground: Color,
    val creamSurface: Color,
    val creamSurfaceVariant: Color,
    val creamCard: Color,
    val warmLinen: Color,
    val linenBorder: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val isDark: Boolean
)

val LightSolariumCustomColors = SolariumCustomColors(
    sunOrange = SolariumSunOrange,
    sunOrangeLight = SolariumSunOrangeLight,
    sunOrangeContainer = SolariumSunOrangeContainer,
    onSunOrangeContainer = OnSolariumSunOrangeContainer,
    softYellow = SolariumSoftYellow,
    softYellowContainer = SolariumSoftYellowContainer,
    onSoftYellowContainer = OnSolariumSoftYellowContainer,
    sunbeam = SolariumSunbeam,
    sunGlow = SolariumSunGlow,
    warmTerracotta = SolariumTerracotta,
    warmTerracottaContainer = SolariumTerracottaContainer,
    onWarmTerracottaContainer = OnSolariumTerracottaContainer,
    creamBackground = SolariumCreamBackground,
    creamSurface = SolariumCreamSurface,
    creamSurfaceVariant = SolariumCreamSurfaceVariant,
    creamCard = SolariumCreamCard,
    warmLinen = SolariumWarmLinen,
    linenBorder = SolariumLinenBorder,
    textPrimary = SolariumTextPrimary,
    textSecondary = SolariumTextSecondary,
    isDark = false
)

val DarkSolariumCustomColors = SolariumCustomColors(
    sunOrange = SolariumPrimaryDark,
    sunOrangeLight = SolariumTertiaryDark,
    sunOrangeContainer = SolariumPrimaryContainerDark,
    onSunOrangeContainer = SolariumSunOrangeContainer,
    softYellow = SolariumTertiaryDark,
    softYellowContainer = SolariumTertiaryContainerDark,
    onSoftYellowContainer = SolariumSoftYellowContainer,
    sunbeam = Color(0xFF2C2218),
    sunGlow = SolariumTertiaryDark,
    warmTerracotta = SolariumSecondaryDark,
    warmTerracottaContainer = SolariumSecondaryContainerDark,
    onWarmTerracottaContainer = SolariumTerracottaContainer,
    creamBackground = SolariumBackgroundDark,
    creamSurface = SolariumSurfaceDark,
    creamSurfaceVariant = SolariumSurfaceVariantDark,
    creamCard = Color(0xFF2B221B),
    warmLinen = Color(0xFF3F3329),
    linenBorder = Color(0xFF4C3D32),
    textPrimary = SolariumOnBackgroundDark,
    textSecondary = SolariumOnSurfaceVariantDark,
    isDark = true
)

val LocalSolariumCustomColors = staticCompositionLocalOf { LightSolariumCustomColors }

object SolariumTheme {
    val colors: SolariumCustomColors
        @androidx.compose.runtime.Composable
        get() = LocalSolariumCustomColors.current
}

// Compatibility aliases for legacy references
val TerracottaPrimary = SolariumSunOrange
val TerracottaDark = SolariumSunOrangeDark
val TerracottaContainer = SolariumSunOrangeContainer
val OnTerracottaContainer = OnSolariumSunOrangeContainer
val SageGreenSecondary = SolariumTerracotta
val SageGreenContainer = SolariumTerracottaContainer
val OnSageGreenContainer = OnSolariumTerracottaContainer
val WarmAmberTertiary = SolariumSoftYellow
val WarmAmberContainer = SolariumSoftYellowContainer
val OnWarmAmberContainer = OnSolariumSoftYellowContainer
val WarmBackground = SolariumCreamBackground
val WarmSurface = SolariumCreamSurface
val WarmSurfaceVariant = SolariumCreamSurfaceVariant
val WarmOutline = SolariumOutline
val TerracottaPrimaryDark = SolariumPrimaryDark
val TerracottaContainerDark = SolariumPrimaryContainerDark
val SageGreenSecondaryDark = SolariumSecondaryDark
val SageGreenContainerDark = SolariumSecondaryContainerDark
val WarmAmberTertiaryDark = SolariumTertiaryDark
val WarmAmberContainerDark = SolariumTertiaryContainerDark
val DarkBackground = SolariumBackgroundDark
val DarkSurface = SolariumSurfaceDark
val DarkSurfaceVariant = SolariumSurfaceVariantDark


