package com.example.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ==========================================
// Base Palette Definitions
// ==========================================

// Deep Navy Branded Palette
val DarkBg = Color(0xFF080F25)
val DarkSurfaceElevated = Color(0xFF14213D)
val DarkSurfaceSecondary = Color(0xFF1D2B4A)
val DarkCardBorder = Color(0xFF2B3D66)

// Neutral Light Palette
val LightBg = Color(0xFFF8FAFC)
val LightSurfaceElevated = Color(0xFFFFFFFF)
val LightSurfaceSecondary = Color(0xFFF1F5F9)
val LightCardBorder = Color(0xFFCBD5E1)

// Accent Colors
val AccentBlue = Color(0xFF8FB5FF)       // High-contrast primary light blue for dark theme
val AccentNavy = Color(0xFF1E3A8A)       // Primary deep navy for light theme
val AccentCyan = Color(0xFF38BDF8)       // Vivid cyan for secondary highlights
val AccentAmber = Color(0xFFFBBF24)      // Amber gold for streak/targets (readable against dark)
val AmberDark = Color(0xFFD97706)        // Amber for light theme
val SuccessEmerald = Color(0xFF34D399)   // Bright emerald for dark theme
val SuccessEmeraldDark = Color(0xFF059669) // Emerald for light theme
val ErrorCrimson = Color(0xFFF87171)     // High-contrast soft red for dark theme
val ErrorCrimsonDark = Color(0xFFDC2626) // Crimson for light theme

// Backward compatibility colors (kept for existing components)
val Navy900 = Color(0xFF0F172A)
val Navy800 = Color(0xFF1E293B)
val Navy700 = Color(0xFF334155)
val NavyPrimary = Color(0xFF1E3A8A)
val NavyPrimaryLight = Color(0xFF2563EB)
val NavyPrimaryContainer = Color(0xFFDBEAFE)
val OnNavyPrimaryContainer = Color(0xFF1E3A8A)
val SkySecondary = Color(0xFF0284C7)
val SkySecondaryContainer = Color(0xFFE0F2FE)
val OnSkySecondaryContainer = Color(0xFF0369A1)
val AmberAccent = Color(0xFFD97706)
val AmberContainer = Color(0xFFFEF3C7)
val AmberOnContainer = Color(0xFF92400E)
val EmeraldGreen = Color(0xFF059669)
val EmeraldGreenContainer = Color(0xFFD1FAE5)
val CrimsonError = Color(0xFFDC2626)
val CrimsonErrorContainer = Color(0xFFFEE2E2)
val Slate50 = Color(0xFFF8FAFC)
val Slate100 = Color(0xFFF1F5F9)
val Slate200 = Color(0xFFE2E8F0)
val Slate300 = Color(0xFFCBD5E1)
val Slate400 = Color(0xFF94A3B8)
val Slate500 = Color(0xFF64748B)
val Slate600 = Color(0xFF475569)
val Slate700 = Color(0xFF334155)
val Slate800 = Color(0xFF1E293B)
val Slate900 = Color(0xFF0F172A)
