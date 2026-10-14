package com.example.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Centralized Semantic Color Palette for IELTS Journey.
 * Strictly adheres to WCAG 2.2 AA contrast standards (> 4.5:1 for normal text, > 3:1 for large/graphical).
 */
data class AppColors(
    val isDark: Boolean,
    val background: Color,
    val surface: Color,
    val surfaceElevated: Color,
    val surfaceVariant: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val accent: Color,
    val accentOn: Color,
    val accentContainer: Color,
    val accentOnContainer: Color,
    val border: Color,
    val borderSubtle: Color,
    val iconPrimary: Color,
    val iconSecondary: Color,
    val progressTrack: Color,
    val progressIndicator: Color,
    val success: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,
    val warning: Color,
    val warningContainer: Color,
    val onWarningContainer: Color,
    val error: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val chipBackground: Color,
    val chipSelectedBackground: Color,
    val chipText: Color,
    val chipSelectedText: Color
)

val DarkAppColors = AppColors(
    isDark = true,
    background = Color(0xFF080F25),             // Deep Midnight Navy
    surface = Color(0xFF14213D),                // Elevated Surface
    surfaceElevated = Color(0xFF1A2A4C),        // Higher elevation / dialogs
    surfaceVariant = Color(0xFF1D2B4A),         // Secondary surface
    textPrimary = Color(0xFFFFFFFF),            // Pure White (14+:1 on surface)
    textSecondary = Color(0xFFE2E8F0),          // High readability light slate (10+:1)
    textMuted = Color(0xFFB0C4DE),              // Readable muted slate (> 7:1)
    accent = Color(0xFF8FB5FF),                 // Accessible bright azure (> 7:1 on surface)
    accentOn = Color(0xFF080F25),
    accentContainer = Color(0xFF1E3A8A),
    accentOnContainer = Color(0xFFDBEAFE),
    border = Color(0xFF334A70),
    borderSubtle = Color(0xFF223555),
    iconPrimary = Color(0xFFFFFFFF),
    iconSecondary = Color(0xFFB0C4DE),
    progressTrack = Color(0xFF263859),          // Contrast track on dark surface
    progressIndicator = Color(0xFF8FB5FF),      // Vibrant progress indicator
    success = Color(0xFF34D399),                // Bright Emerald (> 6:1)
    successContainer = Color(0xFF064E3B),
    onSuccessContainer = Color(0xFFA7F3D0),
    warning = Color(0xFFFBBF24),                // Bright Amber (> 8:1)
    warningContainer = Color(0xFF78350F),
    onWarningContainer = Color(0xFFFDE68A),
    error = Color(0xFFF87171),                  // Bright Coral red (> 5:1)
    errorContainer = Color(0xFF7F1D1D),
    onErrorContainer = Color(0xFFFECACA),
    chipBackground = Color(0xFF1A2640),
    chipSelectedBackground = Color(0xFF8FB5FF),
    chipText = Color(0xFFE2E8F0),
    chipSelectedText = Color(0xFF080F25)
)

val LightAppColors = AppColors(
    isDark = false,
    background = Color(0xFFF8FAFC),            // Slate 50
    surface = Color(0xFFFFFFFF),               // Pure White
    surfaceElevated = Color(0xFFF1F5F9),       // Slate 100
    surfaceVariant = Color(0xFFE2E8F0),        // Slate 200
    textPrimary = Color(0xFF0F172A),           // Slate 900 (> 14:1)
    textSecondary = Color(0xFF334155),         // Slate 700 (> 8:1)
    textMuted = Color(0xFF475569),             // Slate 600 (> 5.5:1)
    accent = Color(0xFF1D4ED8),                // Blue 700 (> 7:1 on white)
    accentOn = Color(0xFFFFFFFF),
    accentContainer = Color(0xFFDBEAFE),
    accentOnContainer = Color(0xFF1E3A8A),
    border = Color(0xFFCBD5E1),
    borderSubtle = Color(0xFFE2E8F0),
    iconPrimary = Color(0xFF0F172A),
    iconSecondary = Color(0xFF475569),
    progressTrack = Color(0xFFE2E8F0),
    progressIndicator = Color(0xFF1D4ED8),
    success = Color(0xFF059669),
    successContainer = Color(0xFFD1FAE5),
    onSuccessContainer = Color(0xFF065F46),
    warning = Color(0xFFD97706),
    warningContainer = Color(0xFFFEF3C7),
    onWarningContainer = Color(0xFF92400E),
    error = Color(0xFFDC2626),
    errorContainer = Color(0xFFFEE2E2),
    onErrorContainer = Color(0xFF991B1B),
    chipBackground = Color(0xFFF1F5F9),
    chipSelectedBackground = Color(0xFF1D4ED8),
    chipText = Color(0xFF334155),
    chipSelectedText = Color(0xFFFFFFFF)
)

val LocalAppColors = staticCompositionLocalOf { DarkAppColors }

/**
 * Convenience accessor for theme-aware semantic tokens anywhere in Compose.
 */
object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current
}
