package com.sovereign.gamemaster.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ThemePreset {
    MINIMAL_DARK,
    SUBTLE_NEON
}

// Holds all styling parameters including colors, border dimensions, and corner radii for dynamic UI customization.
data class SovereignThemeConfig(
    val background: Color,   // Primary background color for the application window or root container
    val surface: Color,      // Surface color for cards, panels, and raised elements
    val primary: Color,      // Primary brand or accent color for main interactive elements

    // Borders and Corners
    val borderColor: Color,  // Subtle stroke or divider color to outline containers
    val cornerRadius: Dp,    // Geometric rounding dimension for buttons, cards, and panels
    val borderWidth: Dp,     // Stroke thickness dimension for borders and outlines
    
    // Button Colours
    val successColor: Color, // Semantic color indicating successful actions, online states, or 'Start' controls
    val dangerColor: Color,  // Semantic color indicating destructive actions, errors, or 'Stop' controls
    val warningColor: Color, // Semantic color indicating cautions, warnings, or 'Pause' controls

    // Set Font
    val fontPrimary: FontFamily = FontFamily.SansSerif, // Primary font
    val fontTerminal: FontFamily = FontFamily.Monospace, // Terminal font

    // Text Colour
    val textMain: Color,     // High-contrast color for primary body and header text
    val textMuted: Color,    // Low-contrast color for secondary text, metadata, or placeholders
    val textHighlight: Color = Color.Transparent,
    val textSuccess: Color = Color.Transparent,
    val textError: Color = Color.Transparent,
    val textCode: Color = Color.Transparent,
    val textAccent: Color = Color.Transparent,
    val textCustomColor1: Color = Color.Transparent,
    val textCustomColor2: Color = Color.Transparent,
    val textCustomColor3: Color = Color.Transparent,
    val textCustomColor4: Color = Color.Transparent,
    val textCustomColor5: Color = Color.Transparent,

    // Font Sizes
    val fontSizeSmall: TextUnit = 12.sp,
    val fontSizeMedium: TextUnit = 14.sp,
    val fontSizeLarge: TextUnit = 18.sp
) {
    companion object {
        fun fromPreset(preset: ThemePreset): SovereignThemeConfig {
            return when (preset) {
                ThemePreset.MINIMAL_DARK -> SovereignThemeConfig(
                    background = Color(0xFF121417),
                    surface = Color(0xFF1A1D24),
                    primary = Color(0xFF3B82F6),
                    textMain = Color(0xFFE2E8F0),
                    textMuted = Color(0xFF94A3B8),
                    borderColor = Color(0xFF2E3846),
                    successColor = Color(0xFF10B981), // Muted green
                    dangerColor = Color(0xFFEF4444),  // Muted red
                    warningColor = Color(0xFFF59E0B), // Muted amber
                    cornerRadius = 6.dp,
                    borderWidth = 1.dp,
                    fontPrimary = FontFamily.SansSerif,
                    fontTerminal = FontFamily.Monospace
                )
                ThemePreset.SUBTLE_NEON -> SovereignThemeConfig(
                    background = Color(0xFF0D0F12),
                    surface = Color(0xFF14181F),
                    primary = Color(0xFF00FF9D),    // Neon mint primary
                    textMain = Color(0xFFF1F5F9),
                    textMuted = Color(0xFF64748B),
                    borderColor = Color(0xFF1E293B),
                    successColor = Color(0xFF00FF9D), // Neon mint for success
                    dangerColor = Color(0xFFFF3366),  // Sharp neon magenta/red for danger
                    warningColor = Color(0xFFFFD300), // Electric yellow for warning
                    cornerRadius = 2.dp,
                    borderWidth = 1.dp,
                    fontPrimary = FontFamily.Monospace,
                    fontTerminal = FontFamily.Monospace
                )
            }
        }
    }
}